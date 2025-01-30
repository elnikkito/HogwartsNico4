package com.example.HogwartsNico.controllers;

import com.example.HogwartsNico.dtos.requests.HousePostRequestDTO;
import com.example.HogwartsNico.dtos.responses.HouseResponseDTO;
import com.example.HogwartsNico.repositories.HouseService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hogwarts/houses")
public class HouseRestController {
    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<List<HouseResponseDTO>> getAllHouses() {
        List<HouseResponseDTO> casas = houseService.getAllHouses();
        if (casas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(casas); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponseDTO> getHouseById(@PathVariable Long id) {
        HouseResponseDTO house = houseService.getHouseById(id);
        if (house == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(house); // 200 OK
    }

    @PostMapping
    public ResponseEntity<HouseResponseDTO> createHouse(@Valid @RequestBody HousePostRequestDTO housePostRequestDTO){
        HouseResponseDTO newHouse = houseService.createHouse(housePostRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHouse);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<HouseResponseDTO> updateHouse(@PathVariable Long id, @RequestBody JsonNode jsonNode) {
        HouseResponseDTO updatedHouse = houseService.updateHouse(id, jsonNode);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedHouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouseById(id);
        return ResponseEntity.noContent().build();
    }

}
