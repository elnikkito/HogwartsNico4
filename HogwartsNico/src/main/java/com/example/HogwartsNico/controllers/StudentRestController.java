package com.example.HogwartsNico.controllers;

import com.example.HogwartsNico.dtos.requests.StudentPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.StudentResponseDTO;
import com.example.HogwartsNico.repositories.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hogwarts/students")
@RequiredArgsConstructor
public class StudentRestController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> estudiantes = studentService.getAllStudents();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(estudiantes); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO estudiante = studentService.getStudentById(id);
        if (estudiante == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(estudiante); // 200 OK
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentPostRequestDTO studentRequest) {
        StudentResponseDTO newStudent = studentService.createStudent(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<StudentResponseDTO> updatedStudent(@PathVariable Long id, @RequestBody JsonNode jsonNode) {
        StudentResponseDTO updatedStudent = studentService.updateStudent(id, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
