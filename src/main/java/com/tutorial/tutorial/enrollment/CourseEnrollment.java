package com.tutorial.tutorial.enrollment;



import java.time.ZonedDateTime;

import com.tutorial.tutorial.course.Course;
import com.tutorial.tutorial.student.Student;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;

@Entity
public class CourseEnrollment {

    @EmbeddedId
    private EnrollmentId enrollmentId;

    @ManyToOne
    @JoinColumn(
        name = "student_id",//<------ student column inside the enrolloment id
        foreignKey = @ForeignKey(
            name = "enrollment_student_id_fk"
        )
    )
    @MapsId("studentId")//<----- variable name in the EnrollementId class
    private Student student;

    @ManyToOne
    @JoinColumn(
        name = "course_id",//<------ student column inside the enrolloment id
        foreignKey = @ForeignKey(
            name = "enrollment_course_id_fk"
        )
    )
    @MapsId("courseId")//<----- variable name in the EnrollementId class
    private Course course;

    @Column(
        nullable = false
    )
    private ZonedDateTime createdAt;

    

    @PrePersist
    public void prePersist(){
        this.createdAt = ZonedDateTime.now();
    }
    

    public CourseEnrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentId = new EnrollmentId(student.getId(), course.getId());
    }

    public CourseEnrollment() {
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enrollmentId == null) ? 0 : enrollmentId.hashCode());
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
        CourseEnrollment other = (CourseEnrollment) obj;
        if (enrollmentId == null) {
            if (other.enrollmentId != null)
                return false;
        } else if (!enrollmentId.equals(other.enrollmentId))
            return false;
        return true;
    }

    



}
