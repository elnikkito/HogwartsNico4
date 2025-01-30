package com.example.HogwartsNico.mappers;

import com.example.HogwartsNico.dtos.requests.PetPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.PetResponseDTO;
import com.example.HogwartsNico.models.Pet;
import com.example.HogwartsNico.models.Student;
import com.example.HogwartsNico.repositories.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class PetMapper {
    private final StudentRepository studentRepository;

    public PetResponseDTO toDto(Pet mascota) {
        if (mascota == null) {
            return null;
        }
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        dto.setEstudianteNombre(mascota.getEstudiante() != null ? mascota.getEstudiante().getNombre() + " " + mascota.getEstudiante().getApellido() : null);
        return dto;
    }

    public Pet toEntity(PetPostRequestDTO dto) {
        Pet pet = new Pet();
        pet.setNombre(dto.getNombre());
        pet.setEspecie(dto.getEspecie());

        Student student = studentRepository.findById(dto.getIdEstudiante())
                    .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado con id: " + dto.getIdEstudiante()));
        pet.setEstudiante(student);
        return pet;
    }

    public void updateEntityFromDto(JsonNode patchJson, Pet pet) {
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
                        pet.setNombre(nombre);
                    }
                    break;

                case "especie":
                    if (patchJson.get("especie").isNull()) {
                        errores.put("especie", "El campo 'especie' no puede ser null");
                    } else {
                        String especie = patchJson.get("especie").asText();
                        if(especie.isEmpty()) errores.put("especie", "El campo 'especie' no puede estar en blanco null");
                        if(especie.length()> 50) errores.put("especie", "El campo 'especie' no puede superar los 50 caracteres");
                        pet.setEspecie(especie);
                    }
                    break;
                case "idEstudiante":
                    if (patchJson.get("idEstudiante").isNull()) {
                        errores.put("idEstudiante", "El campo 'idEstudiante' no puede ser null");
                    } else {
                        Student student = studentRepository.findById(patchJson.get("idEstudiante").asLong())
                                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + patchJson.get("idEstudiante").asLong()));
                        pet.setEstudiante(student);
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
