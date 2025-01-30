package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.PetPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.PetResponseDTO;
import com.example.HogwartsNico.mappers.PetMapper;
import com.example.HogwartsNico.models.Pet;
import com.example.HogwartsNico.models.Student;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PetServiceImp implements PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final StudentRepository studentRepository;

    @Override
    public List<PetResponseDTO> getAllPets() {
        List<Pet> mascotas = petRepository.findAll();
        return mascotas.stream().map(
                petMapper::toDto
        ).toList();
    }

    @Override
    public PetResponseDTO getPetById(Long id) {
        Pet mascota = petRepository.findById(id).orElse(null);
        return petMapper.toDto(mascota);
    }

    @Override
    public PetResponseDTO createPet(PetPostRequestDTO petPostRequestDTO) {
        Pet pet = petMapper.toEntity(petPostRequestDTO);
        Pet savedPet = petRepository.save(pet);
        return petMapper.toDto(savedPet);
    }

    @Override
    public PetResponseDTO updatePet(Long id, JsonNode petPatchRequestDTO) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mascota no encontrada con id: " + id));

        petMapper.updateEntityFromDto(petPatchRequestDTO, pet);
        Pet updatedPet = petRepository.save(pet);
        return petMapper.toDto(updatedPet);
    }

    @Override
    @Transactional
    public void deletePetById(Long id) {
        Pet mascota = petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mascota no encontrada con id: " + id));

        Student student = studentRepository.findByMascota(mascota);
        if (student != null) {
            student.setMascota(null);
            studentRepository.save(student);
        }

        petRepository.delete(mascota);
    }
}
