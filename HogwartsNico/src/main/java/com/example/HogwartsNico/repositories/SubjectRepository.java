package com.example.HogwartsNico.repositories;

import com.example.HogwartsNico.models.Subject;
import com.example.HogwartsNico.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByProfesor(Teacher teacher);
    @Query("SELECT COUNT(ea) > 0 FROM StudentSubject ea WHERE ea.id.idAsignatura = :id")
    boolean hasEnrolledStudents(@Param("id")Long id);
}

