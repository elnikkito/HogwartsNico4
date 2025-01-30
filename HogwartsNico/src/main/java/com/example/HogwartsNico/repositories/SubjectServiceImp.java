package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.AssignStudentsToSubjectDTO;
import com.example.HogwartsNico.dtos.requests.SubjectPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.SubjectResponseDTO;
import com.example.HogwartsNico.mappers.SubjectMapper;
import com.example.HogwartsNico.models.Student;
import com.example.HogwartsNico.models.StudentSubject;
import com.example.HogwartsNico.models.StudentSubjectKey;
import com.example.HogwartsNico.models.Subject;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImp implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final StudentRepository studentRepository;
    private final StudentSubjectRepository studentSubjectRepository;

    @Override
    public List<SubjectResponseDTO> getAllSubjects() {
        List<Subject> asignaturas = subjectRepository.findAll();
        return asignaturas.stream().map(
                subjectMapper::toDto
        ).collect(Collectors.toList());
    }

    @Override
    public SubjectResponseDTO getSubjectById(Long id) {
        Subject asignatura = subjectRepository.findById(id).orElse(null);
        return subjectMapper.toDto(asignatura);
    }

    @Override
    public SubjectResponseDTO createSubject(SubjectPostRequestDTO subjectPostRequestDTO) {
        Subject subject = subjectMapper.toEntity(subjectPostRequestDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(savedSubject);
    }

    @Override
    public SubjectResponseDTO assignMarks(Long id, AssignStudentsToSubjectDTO assignStudentsToSubjectDTO) {
        Subject asignatura = subjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con id: " + id));

        List<Long> idsInvalidos = new ArrayList<>();
        List<StudentSubject> studentSubjects = new ArrayList<>();

        if (!assignStudentsToSubjectDTO.getEstudiantes().isEmpty()) {
            assignStudentsToSubjectDTO.getEstudiantes().forEach(estudianteNota -> {
                        Student estudiante = studentRepository.findById(estudianteNota.getIdEstudiante()).orElse(null);
                        if (estudiante == null) {
                            idsInvalidos.add(estudianteNota.getIdEstudiante());
                        } else {
                            StudentSubject studentSubject = new StudentSubject();
                            studentSubject.setAsignatura(asignatura);
                            studentSubject.setEstudiante(estudiante);
                            studentSubject.setCalificacion(estudianteNota.getCalificacion());
                            StudentSubjectKey studentSubjectKey = new StudentSubjectKey();
                            studentSubjectKey.setIdAsignatura(asignatura.getIdAsignatura());
                            studentSubjectKey.setIdEstudiante(estudiante.getIdEstudiante());
                            studentSubject.setId(studentSubjectKey);
                            studentSubjects.add(studentSubject);
                        }
                    }
            );
        }

        if (!idsInvalidos.isEmpty()) {
            throw new NoSuchElementException(
                    "No se encontraron estudiantes con los siguientes IDs: " + idsInvalidos
            );
        }

        studentSubjectRepository.saveAll(studentSubjects);
        Subject updatedSubject = subjectRepository.findById(id).orElse(null);
        return subjectMapper.toDto(updatedSubject);

    }

    @Override
    public SubjectResponseDTO updateSubject(Long id, JsonNode subjectPatchRequestDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con id: " + id));

        subjectMapper.updateEntityFromDto(subjectPatchRequestDTO, subject);
        Subject updatedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(updatedSubject);
    }

    @Override
    public SubjectResponseDTO updateMark(Long subjectId, Long studentId, JsonNode subjectPatchRequestDTO) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con id: " + subjectId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con id: " + studentId));

        StudentSubjectKey studentSubjectKey = new StudentSubjectKey();
        studentSubjectKey.setIdAsignatura(subject.getIdAsignatura());
        studentSubjectKey.setIdEstudiante(student.getIdEstudiante());

        StudentSubject studentSubject = studentSubjectRepository.findById(studentSubjectKey)
                .orElseThrow(() -> new NoSuchElementException("El alumno " + studentId + " no está matriculado en la asignatura " + subjectId));


        subjectPatchRequestDTO.fieldNames().forEachRemaining(campo -> {
            if (campo.equals("calificacion")) {
                if (subjectPatchRequestDTO.get("calificacion").isNull()) {
                    throw new NoSuchElementException("El campo 'calificacion' es obligatorio");
                } else {
                    double calificacion = subjectPatchRequestDTO.get("calificacion").asDouble();
                    if (calificacion < 0) throw new IllegalArgumentException("El campo 'calificacion' es negativo");
                    if (calificacion > 10)
                        throw new IllegalArgumentException("El campo 'calificacion' es mayor que 10");
                    studentSubject.setCalificacion(calificacion);
                }
            } else {
                throw new NoSuchElementException("El campo 'calificacion' es obligatorio");
            }

        });

        studentSubjectRepository.save(studentSubject);
        return subjectMapper.toDto(subjectRepository.findById(subjectId).orElse(null));

    }

    @Override
    @Transactional
    public void deleteSubjectById(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NoSuchElementException("Asignatura no encontrada con id: " + id);
        }
        if (subjectRepository.hasEnrolledStudents(id)) {
            throw new IllegalArgumentException("No se puede borrar la asignatura con id "
                    + id + " porque tiene estudiantes matriculados");
        }
        subjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteStudentFromSubject(Long idStudent, Long idSubject) {

        Student student = studentRepository.findById(idStudent)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado"));
        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada"));

        Optional<StudentSubject> studentSubjectOptional = studentSubjectRepository.findByEstudianteAndAsignatura(student, subject);

        StudentSubject studentSubject = studentSubjectOptional.get();

        studentSubjectRepository.delete(studentSubject);
    }


}
