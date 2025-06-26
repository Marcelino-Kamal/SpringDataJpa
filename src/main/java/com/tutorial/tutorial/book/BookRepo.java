package com.tutorial.tutorial.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.tutorial.DTO.BookDto;

public interface BookRepo extends JpaRepository<Book,Long> {

    @Query(
        "SELECT new com.tutorial.tutorial.DTO.BookDto(b.id,b.BookName,b.createdAt) FROM Book b"
        )
    List<BookDto> getAllBooksDto();
    @Query(
    "SELECT new com.tutorial.tutorial.DTO.BookDto(b.id,b.BookName,b.createdAt) FROM Book b WHERE b.id=?1"
    )
    Optional<BookDto>getBookDtoById(Long id);
}
