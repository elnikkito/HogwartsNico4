package com.example.HogwartsNico.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentPostRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "El apellido es obligatorio")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotNull(message = "El año de curso es obligatorio")
    @Min(value = 1, message = "El año debe ser al menos 1")
    @Max(value = 8, message = "El año debe ser como máximo 8")
    private Integer anyoCurso;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    private Long idCasa;
}