package com.example.HogwartsNico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Profesor")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long idProfesor;

    private String nombre;
    private String apellido;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id_asignatura")
    private Subject asignatura;

    @OneToOne(mappedBy = "profesor")
    @JsonBackReference
    private House house;
}