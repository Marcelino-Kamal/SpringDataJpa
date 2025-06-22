package com.tutorial.tutorial.course;

import java.util.HashSet;
import java.util.Set;

import com.tutorial.tutorial.student.Student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "courses"
)
public class Course {

    @Id
    @SequenceGenerator(
        name = "course_sequence",
        sequenceName = "course_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "course_sequence"
    )
    private Long id;

    @Column(
        nullable = false,
        columnDefinition = "TEXT",
        unique = true
    )
    private String title;

    @Column(
        columnDefinition = "Text",
        nullable = false
    )
    private String department;

    @ManyToMany(
        mappedBy = "courses"
    )
    private Set<Student> students = new HashSet<>();



    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }


    public Course() {
    }
    
    

    public Course(String title, String department) {
        this.title = title;
        this.department = department;
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }

    public void enroll(Student student){
        if(!students.contains(student)){
            students.add(student);
        }
    }

    public void dropCourse(Student student){
        students.remove(student);
    }
        

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", department='" + getDepartment() + "'" +
            
            "}";
    }

}
