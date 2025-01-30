package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.AssignStudentsToSubjectDTO;
import com.example.HogwartsNico.dtos.requests.SubjectPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.SubjectResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface SubjectService {
    List<SubjectResponseDTO> getAllSubjects();
    SubjectResponseDTO getSubjectById(Long id);
    SubjectResponseDTO createSubject(SubjectPostRequestDTO subjectPostRequestDTO);
    SubjectResponseDTO assignMarks(Long id, AssignStudentsToSubjectDTO assignStudentsToSubjectDTO);
    SubjectResponseDTO updateSubject(Long id, JsonNode subjectPatchRequestDTO);
    SubjectResponseDTO updateMark(Long subjectId, Long studentId, JsonNode subjectPatchRequestDTO);
    void deleteSubjectById(Long id);
    void deleteStudentFromSubject(Long idEstudiante, Long idAsignatura);
}
