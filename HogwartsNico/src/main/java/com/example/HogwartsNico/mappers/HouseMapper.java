package com.example.HogwartsNico.mappers;

import com.example.HogwartsNico.dtos.requests.HousePostRequestDTO;
import com.example.HogwartsNico.dtos.responses.HouseResponseDTO;
import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Teacher;
import com.example.HogwartsNico.repositories.TeacherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class HouseMapper {
    private final TeacherRepository teacherRepository;

    public HouseResponseDTO toDto(House casa) {
        if (casa == null) {
            return null;
        }
        HouseResponseDTO dto = new HouseResponseDTO();
        dto.setId(casa.getIdCasa());
        dto.setNombre(casa.getNombre());
        dto.setFundador(casa.getFundador());
        dto.setFantasma(casa.getFantasma());
        dto.setJefeNombre(casa.getProfesor() != null ? casa.getProfesor().getNombre() + " " + casa.getProfesor().getApellido() : null);
        dto.setEstudiantes(casa.getEstudiantes().stream()
                .map(estudiante -> estudiante.getNombre() + " " + estudiante.getApellido())
                .toList());
        return dto;
    }

    public House toEntity(HousePostRequestDTO dto){
        House house = new House();
        house.setNombre(dto.getNombre());
        house.setFantasma(dto.getFantasma());
        house.setFundador(dto.getFundador());

        if(dto.getIdJefe() != null){
            Teacher teacher = teacherRepository.findById(dto.getIdJefe())
                    .orElseThrow(() -> new NoSuchElementException("Profesor no encontrado con id: " + dto.getIdJefe()));
            house.setProfesor(teacher);
        }
        return house;
    }

    public void updateEntityFromDto(JsonNode patchJson, House house) {
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
                        house.setNombre(nombre);
                    }
                    break;

                case "fundador":
                    if (patchJson.get("fundador").isNull()) {
                        errores.put("fundador", "El campo 'fundador' no puede ser null");
                    } else {
                        String fundador = patchJson.get("fundador").asText();
                        if(fundador.isEmpty()) errores.put("fundador", "El campo 'fundador' no puede estar en blanco null");
                        if(fundador.length()> 50) errores.put("fundador", "El campo 'fundador' no puede superar los 50 caracteres");
                        house.setFundador(fundador);
                    }
                    break;
                case "fantasma":
                    if (patchJson.get("fantasma").isNull()) {
                        errores.put("fantasma", "El campo 'fantasma' no puede ser null");
                    } else {
                        String fantasma = patchJson.get("fantasma").asText();
                        if(fantasma.isEmpty()) errores.put("fantasma", "El campo 'fantasma' no puede estar en blanco null");
                        if(fantasma.length()> 50) errores.put("fantasma", "El campo 'fantasma' no puede superar los 50 caracteres");
                        house.setFantasma(fantasma);
                    }
                    break;
                case "idJefe":
                    if (patchJson.get("idJefe").isNull()) {
                        house.setProfesor(null);
                    } else {
                        Teacher teacher = teacherRepository.findById(patchJson.get("idJefe").asLong())
                                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + patchJson.get("ifJefe").asLong()));
                        house.setProfesor(teacher);
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
