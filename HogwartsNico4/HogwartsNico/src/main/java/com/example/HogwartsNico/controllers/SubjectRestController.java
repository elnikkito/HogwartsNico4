package com.example.HogwartsNico.controllers;

import com.example.HogwartsNico.dtos.requests.AssignStudentsToSubjectDTO;
import com.example.HogwartsNico.dtos.requests.SubjectPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.SubjectResponseDTO;
import com.example.HogwartsNico.repositories.SubjectService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hogwarts/subjects")
@RequiredArgsConstructor
public class SubjectRestController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        List<SubjectResponseDTO> asignaturas = subjectService.getAllSubjects();
        if (asignaturas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(asignaturas); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable Long id) {
        SubjectResponseDTO asignatura = subjectService.getSubjectById(id);
        if (asignatura == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(asignatura); // 200 OK
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectPostRequestDTO subjectPostRequestDTO) {
        SubjectResponseDTO newSubject = subjectService.createSubject(subjectPostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubject);
    }

    @PostMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> assignStudents(@PathVariable Long id, @Valid @RequestBody AssignStudentsToSubjectDTO assignStudentsToSubjectDTO){
        SubjectResponseDTO updatedSubject = subjectService.assignMarks(id, assignStudentsToSubjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedSubject);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<SubjectResponseDTO> updateSubject(@PathVariable Long id, @RequestBody JsonNode jsonNode) {
        SubjectResponseDTO updatedSubject = subjectService.updateSubject(id, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedSubject);
    }

    @PatchMapping({"/{subjectId}/students/{studentId}"})
    public ResponseEntity<SubjectResponseDTO> updateStudentMark(@PathVariable Long subjectId, @PathVariable Long studentId, @RequestBody JsonNode jsonNode) {
        SubjectResponseDTO updatedMark = subjectService.updateMark(subjectId, studentId, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedMark);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idSubject}/students/{idStudent}")
    public ResponseEntity<Void> deleteStudentsForSubject(@PathVariable Long idSubject, @PathVariable Long idStudent) {
        subjectService.deleteStudentFromSubject(idStudent, idSubject);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
