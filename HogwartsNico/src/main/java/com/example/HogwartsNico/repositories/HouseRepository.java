package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
    House findByProfesor(Teacher teacher);
}

