package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
