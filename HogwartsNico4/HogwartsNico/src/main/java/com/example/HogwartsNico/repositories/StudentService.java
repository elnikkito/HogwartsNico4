package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.StudentPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.StudentResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface StudentService {
    List<StudentResponseDTO> getAllStudents();
    StudentResponseDTO getStudentById(Long id);
    StudentResponseDTO createStudent(StudentPostRequestDTO studentRequestDTO);
    StudentResponseDTO updateStudent(Long id, JsonNode studentPatchRequestDTO);
    void deleteStudentById(Long id);
}
