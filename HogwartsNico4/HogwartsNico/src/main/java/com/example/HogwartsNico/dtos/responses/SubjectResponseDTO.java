package com.example.HogwartsNico.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class SubjectResponseDTO {
    private Long id;
    private String nombre;
    private String aula;
    private boolean obligatoria;
    private String profesorNombre; // Nombre del profesor
    private List<StudentGradeDTO> estudiantes; // Nombres de los estudiantes inscritos

    @Data
    public static class StudentGradeDTO {
        private String nombre; // Nombre del estudiante
        private double calificacion; // Calificación del estudiante en esta asignatura
    }
}
