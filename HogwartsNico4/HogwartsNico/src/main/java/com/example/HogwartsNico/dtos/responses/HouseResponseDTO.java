package com.example.HogwartsNico.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class HouseResponseDTO {
    private Long id;
    private String nombre;
    private String fundador;
    private String fantasma;
    private String jefeNombre; // Nombre del jefe de casa
    private List<String> estudiantes; // Nombres de los estudiantes
}
