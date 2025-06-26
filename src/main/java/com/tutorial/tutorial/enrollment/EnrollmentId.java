package com.tutorial.tutorial.enrollment;

import jakarta.persistence.Embeddable;


// this annotation mean this class can be embedded into other entities
@Embeddable
public class EnrollmentId {


    private Long studentId;
    private Long courseId;



    public EnrollmentId() {
    }

    

    public EnrollmentId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    

    public Long getStudentId() {
        return studentId;
    }



    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }



    public Long getCourseId() {
        return courseId;
    }



    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "{" +
            " studentId='" + getStudentId() + "'" +
            ", courseId='" + getCourseId() + "'" +
            "}";
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
        result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
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
        EnrollmentId other = (EnrollmentId) obj;
        if (studentId == null) {
            if (other.studentId != null)
                return false;
        } else if (!studentId.equals(other.studentId))
            return false;
        if (courseId == null) {
            if (other.courseId != null)
                return false;
        } else if (!courseId.equals(other.courseId))
            return false;
        return true;
    }
    

}
