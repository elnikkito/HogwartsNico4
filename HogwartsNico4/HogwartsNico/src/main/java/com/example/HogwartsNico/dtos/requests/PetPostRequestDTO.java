package com.example.HogwartsNico.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetPostRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "La especie es obligatoria")
    @NotBlank(message = "La especie no puede estar vacía")
    @Size(max = 50, message = "La especie no puede superar los 50 caracteres")
    private String especie;

    @NotNull(message = "El idEstudiante es obligatorio")
    private Long idEstudiante;
}
