package com.example.HogwartsNico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Asignatura")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    private Long idAsignatura;

    private String nombre;

    private String aula;

    private Boolean obligatoria;

    @OneToOne(mappedBy = "asignatura")
    @JsonManagedReference
    private Teacher profesor;

    @OneToMany(mappedBy = "asignatura")
    @JsonBackReference
    private List<StudentSubject> notas = new ArrayList<>();
}

