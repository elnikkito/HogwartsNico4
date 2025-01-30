package com.example.HogwartsNico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "Mascota")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long idMascota;

    private String nombre;

    private String especie;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id_estudiante")
    private Student estudiante;
}