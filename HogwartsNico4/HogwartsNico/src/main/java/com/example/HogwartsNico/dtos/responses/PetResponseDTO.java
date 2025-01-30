package com.example.HogwartsNico.dtos.responses;

import lombok.Data;

@Data
public class PetResponseDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String estudianteNombre; // Nombre del estudiante dueño
}
