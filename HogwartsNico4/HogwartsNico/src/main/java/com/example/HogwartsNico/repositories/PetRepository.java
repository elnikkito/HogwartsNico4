package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
