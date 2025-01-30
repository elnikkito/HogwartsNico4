package com.example.HogwartsNico.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Estudiante_Asignatura")
public class StudentSubject {

    @EmbeddedId
    private StudentSubjectKey id;

    @ManyToOne
    @MapsId("idEstudiante")
    @JoinColumn(name = "id_estudiante")
    @JsonBackReference
    private Student estudiante;

    @ManyToOne
    @MapsId("idAsignatura")
    @JoinColumn(name = "id_asignatura")
    private Subject asignatura;

    @Column(name = "calificacion")
    private Double calificacion;
}
