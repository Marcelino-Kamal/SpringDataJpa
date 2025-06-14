package com.tutorial.tutorial.card;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentCardRepo extends JpaRepository<StudentCard,Long> {

    @Query("SELECT c FROM StudentCard c JOIN FETCH c.student WHERE c.id = ?1")
    Optional<StudentCard> findStudentCardWithStudent(Long id);

}
