package com.tutorial.tutorial.student;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    //the One to one is default is EAGER
    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = false)
    private StudentCard studentCard;
    // THE ONE TO MANY is deafualt LAZY
    @OneToMany(
        cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
        mappedBy = "student",
        orphanRemoval = true
        
    )
    private Set<Book> books = new HashSet<>() ;

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


    public Set<Book> getBooks() { return this.books;  }

    public void setBooks(Set<Book> books) { this.books = books;}

    public void addBook(Book book){
        if(!books.contains(book)){
            books.add(book);
            book.setStudent(this);
        }
    }
    public void removeBook(Book book){
        if(books.contains(book)){
            books.remove(book);
            book.setStudent(null);
        }else{
            System.out.println("No Book Found");
        }
    }


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
