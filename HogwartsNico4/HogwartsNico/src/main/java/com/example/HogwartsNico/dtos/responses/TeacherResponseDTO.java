package com.example.HogwartsNico.dtos.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaInicio;
    private String asignaturaNombre; // Nombre de la asignatura
}
