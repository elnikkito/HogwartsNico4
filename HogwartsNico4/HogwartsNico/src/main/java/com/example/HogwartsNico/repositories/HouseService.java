package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.HousePostRequestDTO;
import com.example.HogwartsNico.dtos.responses.HouseResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface HouseService {
    List<HouseResponseDTO> getAllHouses();
    HouseResponseDTO getHouseById(Long id);
    HouseResponseDTO createHouse(HousePostRequestDTO housePostRequestDTO);
    HouseResponseDTO updateHouse(Long id, JsonNode housePatchRequestDTO);
    void deleteHouseById(Long id);

}
