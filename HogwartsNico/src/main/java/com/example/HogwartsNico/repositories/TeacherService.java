package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.TeacherPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.TeacherResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface TeacherService {
    List<TeacherResponseDTO> getAllTeachers();
    TeacherResponseDTO getTeacherbyId(Long id);
    TeacherResponseDTO createTeacher(TeacherPostRequestDTO teacherPostRequestDTO);
    TeacherResponseDTO updateTeacher(Long id, JsonNode teacherPatchRequestDTO);
    void deleteTeacherById(Long id);
}
