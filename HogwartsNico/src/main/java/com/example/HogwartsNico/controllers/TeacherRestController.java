package com.example.HogwartsNico.controllers;

import com.example.HogwartsNico.dtos.requests.TeacherPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.TeacherResponseDTO;
import com.example.HogwartsNico.repositories.TeacherService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hogwarts/teachers")
@RequiredArgsConstructor
public class TeacherRestController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        List<TeacherResponseDTO> profesores = teacherService.getAllTeachers();
        if (profesores.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(profesores); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        TeacherResponseDTO profesor = teacherService.getTeacherbyId(id);
        if (profesor == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(profesor); // 200 OK
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(@Valid @RequestBody TeacherPostRequestDTO teacherPostRequestDTO){
        TeacherResponseDTO newTeacher = teacherService.createTeacher(teacherPostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTeacher);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id, @RequestBody JsonNode jsonNode) {
        TeacherResponseDTO updatedTeacher = teacherService.updateTeacher(id, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }
}
