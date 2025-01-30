package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.Student;
import com.example.HogwartsNico.models.Subject;
import com.example.HogwartsNico.models.StudentSubject;
import com.example.HogwartsNico.models.StudentSubjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, StudentSubjectKey> {
    Optional<StudentSubject> findByEstudianteAndAsignatura(Student estudiante, Subject asignatura);
}