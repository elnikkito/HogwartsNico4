package com.example.HogwartsNico.mappers;

import com.example.HogwartsNico.dtos.requests.SubjectPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.SubjectResponseDTO;
import com.example.HogwartsNico.models.Subject;
import com.example.HogwartsNico.repositories.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SubjectMapper {
    private final StudentRepository studentRepository;

    public SubjectResponseDTO toDto(Subject asignatura) {
        if (asignatura == null) {
            return null;
        }
        SubjectResponseDTO dto = new SubjectResponseDTO();
        dto.setId(asignatura.getIdAsignatura());
        dto.setNombre(asignatura.getNombre());
        dto.setAula(asignatura.getAula());
        dto.setObligatoria(asignatura.getObligatoria());
        dto.setProfesorNombre(asignatura.getProfesor() != null ? asignatura.getProfesor().getNombre() + " " + asignatura.getProfesor().getApellido() : null);
        dto.setEstudiantes(asignatura.getNotas().stream()
                .map(estudianteAsignatura -> {
                    SubjectResponseDTO.StudentGradeDTO estudianteDto = new SubjectResponseDTO.StudentGradeDTO();
                    estudianteDto.setNombre(estudianteAsignatura.getEstudiante().getNombre() + " " + estudianteAsignatura.getEstudiante().getApellido());
                    estudianteDto.setCalificacion(estudianteAsignatura.getCalificacion());
                    return estudianteDto;
                })
                .toList());
        return dto;
    }

    public Subject toEntity(SubjectPostRequestDTO dto) {
        Subject subject = new Subject();
        subject.setNombre(dto.getNombre());
        subject.setAula(dto.getAula());
        subject.setObligatoria(dto.getObligatoria());
        return subject;
    }

    public void updateEntityFromDto(JsonNode patchJson, Subject subject) {
        Map<String, String> errores = new HashMap<>();
        patchJson.fieldNames().forEachRemaining(campo -> {
            switch (campo) {
                case "nombre":
                    if (patchJson.get("nombre").isNull()) {
                        errores.put("nombre", "El campo 'nombre' no puede ser null");
                    } else {
                        String nombre = patchJson.get("nombre").asText();
                        if(nombre.isEmpty()) errores.put("nombre", "El campo 'nombre' no puede estar en blanco null");
                        if(nombre.length()> 50) errores.put("nombre", "El campo 'nombre' no puede superar los 100 caracteres");
                        subject.setNombre(nombre);
                    }
                    break;

                case "aula":
                    if (patchJson.get("aula").isNull()) {
                        errores.put("aula", "El campo 'aula' no puede ser null");
                    } else {
                        String aula = patchJson.get("aula").asText();
                        if(aula.isEmpty()) errores.put("aula", "El campo 'aula' no puede estar en blanco null");
                        if(aula.length()> 50) errores.put("aula", "El campo 'aula' no puede superar los 50 caracteres");
                        subject.setAula(aula);
                    }
                    break;

                case "obligatoria":
                    if (patchJson.get("obligatoria").isNull()) {
                        errores.put("obligatoria", "El campo 'obligatoria' no puede ser null");
                    } else {
                        Boolean obligatoria = patchJson.get("obligatoria").asBoolean();
                        subject.setObligatoria(obligatoria);
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
