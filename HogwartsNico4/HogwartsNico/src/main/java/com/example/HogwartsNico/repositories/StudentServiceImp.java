package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.StudentPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.StudentResponseDTO;
import com.example.HogwartsNico.mappers.StudentMapper;
import com.example.HogwartsNico.models.Student;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> estudiantes = studentRepository.findAll();
        return estudiantes.stream().map(
                studentMapper::toDto
        ).toList();
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student estudiante = studentRepository.findById(id).orElse(null);
        return studentMapper.toDto(estudiante);
    }

    @Override
    public StudentResponseDTO createStudent(StudentPostRequestDTO studentRequestDTO) {
        Student student = studentMapper.toEntity(studentRequestDTO);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, JsonNode studentPatchRequestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado con id: " + id));

        studentMapper.updateEntityFromDto(studentPatchRequestDTO, student);
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }
    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado con id: " + id));
        studentRepository.delete(existing);
    }


}