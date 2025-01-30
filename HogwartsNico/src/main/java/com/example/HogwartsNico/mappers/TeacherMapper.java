package com.example.HogwartsNico.mappers;

import com.example.HogwartsNico.dtos.requests.TeacherPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.TeacherResponseDTO;
import com.example.HogwartsNico.models.Subject;
import com.example.HogwartsNico.models.Teacher;
import com.example.HogwartsNico.repositories.SubjectRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class TeacherMapper {
    private final SubjectRepository subjectRepository;

    public TeacherResponseDTO toDto(Teacher profesor) {
        if (profesor == null) {
            return null;
        }
        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(profesor.getIdProfesor());
        dto.setNombre(profesor.getNombre());
        dto.setApellido(profesor.getApellido());
        dto.setFechaInicio(profesor.getFechaInicio());
        dto.setAsignaturaNombre(profesor.getAsignatura() != null ? profesor.getAsignatura().getNombre() : null);
        return dto;
    }

    public Teacher toEntity(TeacherPostRequestDTO dto){
        Teacher teacher = new Teacher();
        teacher.setNombre(dto.getNombre());
        teacher.setApellido(dto.getApellido());
        teacher.setFechaInicio(LocalDate.now());

        if(dto.getIdAsignatura() != null){
            Subject asignatura = subjectRepository.findById(dto.getIdAsignatura())
                    .orElseThrow(() -> new NoSuchElementException("Asignatura no encontrada con id: " + dto.getIdAsignatura()));
            teacher.setAsignatura(asignatura);
        }
        return teacher;
    }

    public void updateEntityFromDto(JsonNode patchJson, Teacher teacher) {
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
                        teacher.setNombre(nombre);
                    }
                    break;

                case "apellido":
                    if (patchJson.get("apellido").isNull()) {
                        errores.put("apellido", "El campo 'apellido' no puede ser null");
                    } else {
                        String apellido = patchJson.get("apellido").asText();
                        if(apellido.isEmpty()) errores.put("apellido", "El campo 'apellido' no puede estar en blanco null");
                        if(apellido.length()> 50) errores.put("apellido", "El campo 'apellido' no puede superar los 50 caracteres");
                        teacher.setApellido(apellido);
                    }
                    break;
                case "idAsignatura":
                    if (patchJson.get("idAsignatura").isNull()) {
                        teacher.setAsignatura(null);
                    } else {
                        Subject asignatura = subjectRepository.findById(patchJson.get("idAsignatura").asLong())
                                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + patchJson.get("idCasa").asLong()));
                        teacher.setAsignatura(asignatura);
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
