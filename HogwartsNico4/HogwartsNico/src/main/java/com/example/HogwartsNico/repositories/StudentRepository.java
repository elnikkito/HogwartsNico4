package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.House;
import com.example.HogwartsNico.models.Pet;
import com.example.HogwartsNico.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCasa(House house);
    Student findByMascota(Pet mascota);
}

