package com.example.HogwartsNico.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SubjectPostRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotNull(message = "El aula es obligatoria")
    @NotBlank(message = "El aula no puede estar vacía")
    @Size(max = 50, message = "El aula no puede superar los 50 caracteres")
    private String aula;

    @NotNull(message = "El campo obligatoria es obligatorio")
    private Boolean obligatoria;
}
