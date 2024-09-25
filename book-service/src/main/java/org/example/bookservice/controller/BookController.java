package org.example.bookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookservice.dto.BookRequestDTO;
import org.example.bookservice.dto.BookResponseDTO;
import org.example.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books") //todo: Так же поменял /api/v1/books на /books
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Получить книгу")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Получить все книги")
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "Получить книгу по её ISBN")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @Operation(summary = "Создать книгу")
    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @Operation(summary = "Обновить книгу")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @Operation(summary = "Удалить книгу")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}