package com.example.HogwartsNico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Casa")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_casa")
    private Long idCasa;

    private String nombre;

    private String fantasma;

    private String fundador;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "id_jefe")
    private Teacher profesor;

    @OneToMany(mappedBy = "casa")
    @JsonBackReference
    private List<Student> estudiantes;
}