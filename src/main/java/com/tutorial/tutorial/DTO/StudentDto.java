package com.tutorial.tutorial.DTO;

import java.time.Instant;
import java.util.Set;

import com.tutorial.tutorial.book.Book;
import com.tutorial.tutorial.enrollment.CourseEnrollment;

public record StudentDto(Long Id,
                        String name,
                        String email,
                        Set<Book> books,
                        Set<CourseEnrollment>courseEnrollments,
                        Instant createAt ) {
}
