package com.example.HogwartsNico.mappers;

import com.example.HogwartsNico.dtos.requests.StudentPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.StudentResponseDTO;
import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Student;
import com.example.HogwartsNico.repositories.HouseRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final HouseRepository houseRepository;

    public StudentResponseDTO toDto(Student estudiante) {
        if (estudiante == null) {
            return null;
        }
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(estudiante.getIdEstudiante());
        dto.setNombre(estudiante.getNombre());
        dto.setApellido(estudiante.getApellido());
        dto.setAnyoCurso(estudiante.getAnyoCurso());
        dto.setFechaNacimiento(estudiante.getFechaNacimiento());
        dto.setCasaNombre(estudiante.getCasa() != null ? estudiante.getCasa().getNombre() : null);
        dto.setMascotaNombre(estudiante.getMascota() != null ? estudiante.getMascota().getNombre() : null);

        // Mapear asignaturas y calificaciones
        dto.setAsignaturas(estudiante.getNotas().stream()
                .map(estudianteAsignatura -> {
                    StudentResponseDTO.SubjectGradeDTO asignaturaDto = new StudentResponseDTO.SubjectGradeDTO();
                    asignaturaDto.setNombre(estudianteAsignatura.getAsignatura().getNombre());
                    asignaturaDto.setCalificacion(estudianteAsignatura.getCalificacion());
                    return asignaturaDto;
                })
                .toList());

        return dto;
    }

    public Student toEntity(StudentPostRequestDTO dto) {
        Student student = new Student();
        student.setNombre(dto.getNombre());
        student.setApellido(dto.getApellido());
        student.setAnyoCurso(dto.getAnyoCurso());
        student.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getIdCasa() != null) {
            House casa = houseRepository.findById(dto.getIdCasa())
                    .orElseThrow(() -> new NoSuchElementException("Casa no encontrada con id: " + dto.getIdCasa()));
            student.setCasa(casa);
        }
        return student;
    }

    public void updateEntityFromDto(JsonNode patchJson, Student student) {
        Map<String, String> errores = new HashMap<>();
        patchJson.fieldNames().forEachRemaining(campo -> {
            switch (campo) {
                case "nombre":
                    if (patchJson.get("nombre").isNull()) {
                        errores.put("nombre", "El campo 'nombre' no puede ser null");
                    } else {
                        String nombre = patchJson.get("nombre").asText();
                        if(nombre.isEmpty()) errores.put("nombre", "El campo 'nombre' no puede estar en blanco null");
                        if(nombre.length()> 50) errores.put("nombre", "El campo 'nombre' no puede superar los 50 caracteres");
                        student.setNombre(nombre);
                    }
                    break;

                case "apellido":
                    if (patchJson.get("apellido").isNull()) {
                        errores.put("apellido", "El campo 'apellido' no puede ser null");
                    } else {
                        String apellido = patchJson.get("apellido").asText();
                        if(apellido.isEmpty()) errores.put("apellido", "El campo 'apellido' no puede estar en blanco null");
                        if(apellido.length()> 50) errores.put("apellido", "El campo 'apellido' no puede superar los 50 caracteres");
                        student.setApellido(apellido);
                    }
                    break;

                case "anyoCurso":
                    if (patchJson.get("anyoCurso").isNull()) {
                        errores.put("anyoCurso", "El campo 'anyoCurso' no puede ser null");
                    } else {
                        int anyoCurso = patchJson.get("anyoCurso").asInt();
                        if(anyoCurso < 1) errores.put("anyoCurso", "El campo 'anyoCurso' no puede ser inferior a 1");
                        if(anyoCurso > 8) errores.put("anyoCurso", "El campo 'anyoCurso' no puede ser superior a 8");
                        student.setAnyoCurso(anyoCurso);
                    }
                    break;

                case "fechaNacimiento":
                    if (patchJson.get("fechaNacimiento").isNull()) {
                        errores.put("fechaNacimiento", "El campo 'fechaNacimiento' no puede ser null");
                    } else {
                        LocalDate fechaNamicimiento = LocalDate.parse(patchJson.get("fechaNacimiento").asText());
                        if(fechaNamicimiento.isAfter(LocalDate.now())) errores.put("fechaNacimiento", "El campo 'fechaNacimiento' no puede ser anterior a hoy");
                        student.setFechaNacimiento(fechaNamicimiento);
                    }
                    break;

                case "idCasa":
                    if (patchJson.get("idCasa").isNull()) {
                        student.setCasa(null);
                    } else {
                        House casa = houseRepository.findById(patchJson.get("idCasa").asLong())
                                .orElseThrow(() -> new RuntimeException("Casa no encontrada con ID: " + patchJson.get("idCasa").asLong()));
                        student.setCasa(casa);
                    }
                    break;
                default:
                    errores.put(campo, "Campo no reconocido: " + campo);
            }
        });

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(errores));
        }
    }
}