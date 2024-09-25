package org.example.bookservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN должен быть в формате ISBN-10 или ISBN-13")
    private String isbn;

    private String name;

    private String genre;

    private String description;

    private String author;
}
