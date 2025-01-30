package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.dtos.requests.HousePostRequestDTO;
import com.example.HogwartsNico.dtos.responses.HouseResponseDTO;
import com.example.HogwartsNico.mappers.HouseMapper;
import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Student;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HouseServiceImp implements HouseService{
    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;
    private final StudentRepository studentRepository;


    @Override
    public List<HouseResponseDTO> getAllHouses() {
        List<House> casas =  houseRepository.findAll();
        return casas.stream().map(
                houseMapper::toDto
        ).toList();
    }

    @Override
    public HouseResponseDTO getHouseById(Long id) {
        House casa = houseRepository.findById(id).orElse(null);
        return houseMapper.toDto(casa);
    }

    @Override
    public HouseResponseDTO createHouse(HousePostRequestDTO housePostRequestDTO) {
        House house = houseMapper.toEntity(housePostRequestDTO);
        House savedHouse = houseRepository.save(house);
        return houseMapper.toDto(savedHouse);
    }

    @Override
    public HouseResponseDTO updateHouse(Long id, JsonNode housePatchRequestDTO) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Casa no encontrada con id: " + id));

        houseMapper.updateEntityFromDto(housePatchRequestDTO, house);
        House updatedHouse = houseRepository.save(house);
        return houseMapper.toDto(updatedHouse);
    }

    @Override
    @Transactional
    public void deleteHouseById(Long id) {
        House casa = houseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Casa no encontrada con id: " + id));


        if (casa.getProfesor() != null) {
            casa.setProfesor(null);
            houseRepository.save(casa);
        }

        List<Student> students = studentRepository.findByCasa(casa);
        for (Student student : students) {
            student.setCasa(null);
            studentRepository.save(student);
        }

        houseRepository.delete(casa);
    }
}
