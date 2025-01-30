package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.TeacherPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.TeacherResponseDTO;
import com.example.HogwartsNico.mappers.TeacherMapper;
import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Subject;
import com.example.HogwartsNico.models.Teacher;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TeacherServiceImp implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final HouseRepository houseRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public List<TeacherResponseDTO> getAllTeachers() {
        List<Teacher> profesores = teacherRepository.findAll();
        return profesores.stream().map(
                teacherMapper::toDto
        ).toList();
    }

    @Override
    public TeacherResponseDTO getTeacherbyId(Long id) {
        Teacher profesor = teacherRepository.findById(id).orElse(null);
        return teacherMapper.toDto(profesor);
    }

    @Override
    public TeacherResponseDTO createTeacher(TeacherPostRequestDTO teacherPostRequestDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherPostRequestDTO);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(savedTeacher);
    }

    @Override
    public TeacherResponseDTO updateTeacher(Long id, JsonNode teacherPatchRequestDTO) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profesor no encontrado con id: " + id));

        teacherMapper.updateEntityFromDto(teacherPatchRequestDTO, teacher);
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(updatedTeacher);
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profesor no encontrado con id: " + id));

        House house = houseRepository.findByProfesor(existing);
        if (house != null) {
            house.setProfesor(null);
            houseRepository.save(house);
        }

        Subject subject = subjectRepository.findByProfesor(existing);
        if (subject != null) {
            subject.setProfesor(null);
            subjectRepository.save(subject);
        }

        teacherRepository.delete(existing);
    }
}
