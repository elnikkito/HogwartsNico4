package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.PetPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.PetResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface PetService {
    List<PetResponseDTO> getAllPets();
    PetResponseDTO getPetById(Long id);
    PetResponseDTO createPet(PetPostRequestDTO petPostRequestDTO);
    PetResponseDTO updatePet(Long id, JsonNode petPatchRequestDTO);
    void deletePetById(Long id);
}
