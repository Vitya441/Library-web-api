package org.example.bookservice.dto;

import lombok.Data;

@Data
public class BookResponseDTO {

    private Long id;

    private String isbn;

    private String name;

    private String genre;

    private String description;

    private String author;
}
