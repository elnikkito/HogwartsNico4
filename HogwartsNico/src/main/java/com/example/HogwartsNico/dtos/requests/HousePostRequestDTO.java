package com.example.HogwartsNico.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HousePostRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "El fundador es obligatorio")
    @NotBlank(message = "El fundador no puede estar vacío")
    @Size(max = 50, message = "El fundador no puede superar los 50 caracteres")
    private String fundador;

    private Long idJefe;

    @NotNull(message = "El fantasma es obligatorio")
    @NotBlank(message = "El fantasma no puede estar vacío")
    @Size(max = 50, message = "El fantasma no puede superar los 50 caracteres")
    private String fantasma;
}


