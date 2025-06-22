package com.tutorial.tutorial.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDate;





@Repository
public interface StudentRepo extends JpaRepository<Student,Long>{

    // Select * from students where email = ?

    Optional<Student> findByEmail(String email);
    List<Student> findByNameContains(String name);
    List<Student> findByDobBetween(LocalDate max,LocalDate min);

    //@Query("SELECT s FROM Student s WHERE s.name = ?1")
    // @Query(
    //     value = "SELECT * FROM students s WHERE s.name = ?",
    //     nativeQuery = true
    // )
    @Query("SELECT s FROM Student s WHERE s.name = :name ")
    List<Student> findByhablola(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.email = ?1")
    int ddeleteByEmail(String email);


    @EntityGraph(attributePaths = {"books", "courses"})
    List<Student> findAll();
}