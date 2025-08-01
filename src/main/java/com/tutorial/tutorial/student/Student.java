package com.tutorial.tutorial.student;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorial.tutorial.book.Book;
import com.tutorial.tutorial.card.StudentCard;
import com.tutorial.tutorial.course.Course;
import com.tutorial.tutorial.enrollment.CourseEnrollment;

import jakarta.persistence.*;
import java.util.Objects;

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
//Override the repo.delete()
@SQLDelete(
    sql = "UPDATE students SET deleted_at= now() WHERE id = ?"
)
//Exclude this a deleted record
@SQLRestriction(
    // get only values if the following sql :
    "deleted_at IS NULL"
)
@EntityListeners({
    AuditingEntityListener.class
})
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

    // @ManyToMany(
    //     cascade = {CascadeType.PERSIST}
    // )
    // We use the following when we create a table without have its class
    // @JoinTable(
    //     name = "course_enrollment", //<---- new Table Name
    //     joinColumns = @JoinColumn(
    //         name = "student_id",
    //         foreignKey = @ForeignKey(
    //             name = "enrollment_student_id_FK"
    //         )
    //     ),
    //     inverseJoinColumns = @JoinColumn(
    //         name = "course_id",
    //         foreignKey = @ForeignKey(
    //             name = "enrollment_course_id_FK"
    //         )
    //     )
    // )

    //private Set<Course> courses = new HashSet<>();

    @OneToMany(
        cascade = CascadeType.PERSIST,
        mappedBy = "student"
    )
    private Set<CourseEnrollment> courseEnrollments= new HashSet<>();

    private ZonedDateTime deletedAt;
    //so it can be audited
    @CreatedDate
    @Column(
        updatable = false
    )
    private Instant createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private Instant modifedAt;
    
    @LastModifiedBy
    private String modifyBy;

    @CreatedBy
    private String createdBy;
    
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
    
    //public Set<Course> getCourses() { return courses; }

    //public void setCourses(Set<Course> courses) { this.courses = courses; }
    public Set<CourseEnrollment> getCourseEnrollments() {return courseEnrollments;}

    public void setCourseEnrollments(Set<CourseEnrollment> courseEnrollments) {this.courseEnrollments = courseEnrollments;}
    
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


    public void addCourseEnrollment(Course c){
        courseEnrollments.add(new CourseEnrollment(this,c));
    }
    public void removeCourseEnrollment(Course c){
        courseEnrollments.removeIf(en->en.getCourse().equals(c));
    }

    // public void addCourse(Course course){
    //     if(!courses.contains(course)){
    //         courses.add(course);
    //         course.enroll(this);
            
    //     }
    // }
    
    // public void removeCourse(Course course){
    //     if(courses.contains(course)){
    //         courses.remove(course);
    //         course.dropCourse(null);
    //     }
    // }
    public ZonedDateTime getDeletedAt() {
        return deletedAt;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", email=" + email + ", dob=" + dob + ", studentCard="
                + studentCard + ", deletedAt=" + deletedAt + ", createdAt=" + createdAt + ", modifedAt=" + modifedAt
                + ", modifyBy=" + modifyBy + ", createdBy=" + createdBy + ", age=" + getAge() + "]";
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifedAt() {
        return modifedAt;
    }

    public void setModifedAt(Instant modifedAt) {
        this.modifedAt = modifedAt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    


    
}
