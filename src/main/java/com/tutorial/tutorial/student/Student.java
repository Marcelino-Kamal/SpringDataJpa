package com.tutorial.tutorial.student;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorial.tutorial.book.Book;
import com.tutorial.tutorial.card.StudentCard;

import jakarta.persistence.*;

@Entity
@Table(
    name = "students",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "email_unique_key",
            columnNames = "email"
        )
    }
)
public class Student {

    @Id
    @SequenceGenerator(
        name = "student_sequence",
        sequenceName = "student_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "student_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(name = "email", columnDefinition = "TEXT", nullable = false)
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd") 
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = false)
    private StudentCard studentCard;
    
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy = "student",
        fetch = FetchType.EAGER
    )
    private List<Book> books;

    @Transient
    private Integer age;

    // Constructors
    //  Empty- Constructor is for JPA to get full control any other 
    //  constructor type aint need
    public Student() {}

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Student(Long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public LocalDate getDob() { return dob; }

    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getAge() { return Period.between(this.dob, LocalDate.now()).getYears(); }

    public void setAge(Integer age) { this.age = age; }

    public StudentCard getStudentCard() { return this.studentCard;}

    public void setStudentCard(StudentCard studentCard) { this.studentCard = studentCard; }


    public List<Book> getBooks() { return this.books;  }

    public void setBooks(List<Book> books) { this.books = books;}


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", dob='" + getDob() + "'" +
            ", studentCard='" + getStudentCard() + "'" +
            ", books='" + getBooks() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
    
}
