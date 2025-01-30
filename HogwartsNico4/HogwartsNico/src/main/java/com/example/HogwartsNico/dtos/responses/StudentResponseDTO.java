package com.example.HogwartsNico.dtos.responses;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private int anyoCurso;
    private LocalDate fechaNacimiento;
    private String casaNombre;
    private String mascotaNombre;
    private List<SubjectGradeDTO> asignaturas;
    @Data
    public static class SubjectGradeDTO {
        private String nombre;
        private double calificacion;
    }
}
