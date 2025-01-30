package com.example.HogwartsNico.controllers;

import com.example.HogwartsNico.dtos.requests.PetPostRequestDTO;
import com.example.HogwartsNico.dtos.responses.PetResponseDTO;
import com.example.HogwartsNico.repositories.PetService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;
;


@RestController
@RequestMapping("/hogwarts/pets")
@RequiredArgsConstructor
public class PetRestController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        List<PetResponseDTO> mascotas = petService.getAllPets();
        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(mascotas); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {
        PetResponseDTO mascota = petService.getPetById(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(mascota); // 200 OK
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> createPet(@Valid @RequestBody PetPostRequestDTO petPostRequestDTO) {
        PetResponseDTO newPet = petService.createPet(petPostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<PetResponseDTO> updatePet(@PathVariable Long id, @RequestBody JsonNode jsonNode) {
        PetResponseDTO updatedPet = petService.updatePet(id, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }
}