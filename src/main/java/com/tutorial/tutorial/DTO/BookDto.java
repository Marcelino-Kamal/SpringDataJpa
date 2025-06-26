package com.tutorial.tutorial.DTO;

import java.time.ZonedDateTime;

public record BookDto(Long Id,
                      String bookName,
                      ZonedDateTime createdAt) {

}
