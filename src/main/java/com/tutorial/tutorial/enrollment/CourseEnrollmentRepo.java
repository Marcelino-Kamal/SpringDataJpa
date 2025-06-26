package com.tutorial.tutorial.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepo extends JpaRepository<CourseEnrollment, EnrollmentId> {

}
