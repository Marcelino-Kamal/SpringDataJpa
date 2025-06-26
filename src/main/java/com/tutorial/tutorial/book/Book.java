package com.tutorial.tutorial.book;

import java.time.ZonedDateTime;

import com.tutorial.tutorial.student.Student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;


@Entity

public class Book {

    @Id
    @SequenceGenerator(
        name = "book_sequence",
        sequenceName = "book_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "book_sequence"
    )
    private Long id;
    
    @Column(
        nullable = false,
        columnDefinition = "TEXT",
        unique = true
    )
    private String BookName;

    @Column(
        nullable = false
    )
    private ZonedDateTime createdAt;
    
    @ManyToOne(
        cascade = CascadeType.MERGE
    )
    @JoinColumn(
        name = "student_book_id",
        referencedColumnName = "id", // <----- id in the Student table
        foreignKey = @ForeignKey(
            name = "student_id_book_id_fk"
        ),
        nullable = false
        
    )
    private Student student;

    public Book() {
    }

    @PrePersist
    public void prePresist(){
        createdAt = ZonedDateTime.now();
    }
    //Setter & Getters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return this.BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + 
        ", BookName=" + BookName + 
        ", createdAt=" + createdAt + "]";
    }
    

    

 
    
}
