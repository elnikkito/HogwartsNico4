package com.example.HogwartsNico.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AssignStudentsToSubjectDTO {

    @NotEmpty(message = "La lista de estudiantes no puede estar vacía")
    @Valid
    private List<StudentWithGradeDto> estudiantes = new ArrayList<>();
    
    @Data
    public static class StudentWithGradeDto {
        @NotNull(message = "El ID del estudiante no puede ser nulo")
        private Long idEstudiante;

        @DecimalMin(value = "0.0", message = "La calificación no puede ser menor a 0")
        @DecimalMax(value = "10.0", message = "La calificación no puede exceder 10")
        private Double calificacion;
    }
}
