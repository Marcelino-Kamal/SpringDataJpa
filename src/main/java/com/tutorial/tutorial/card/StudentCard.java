package com.tutorial.tutorial.card;

import java.time.ZonedDateTime;

import com.tutorial.tutorial.student.Student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;

@Entity
public class StudentCard {
    @Id
    @SequenceGenerator(
        name = "card_sequence",
        sequenceName = "card_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "card_sequence"
        
    )
    private Long id;

    @Column(
        nullable = false,
        columnDefinition = "TEXT",
        unique = true
    )
    private String cardNumber;

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
            
    )
    @JoinColumn(
        name = "student_card_id",
        referencedColumnName = "id", // <----- id in the Student table
        foreignKey = @ForeignKey(
            name = "student_id_card_id_fk"
        ),
        nullable = false,
        unique = true
    )
    private Student student;

    @Column(
        nullable = false
    )
    private ZonedDateTime createdAt;

    public StudentCard() {}

    @PrePersist
    public void prePresist(){
        createdAt = ZonedDateTime.now();
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

   
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
   

    
}
