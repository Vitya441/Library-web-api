package com.example.library_service.controller;

import com.example.library_service.dto.RecordRequestDTO;
import com.example.library_service.dto.RecordResponseDTO;
import com.example.library_service.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@RestController
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Operation(summary = "Регистрация книги в библиотеке", description = "Данный эндпоинт вызывается сервисом BookService при создании новой книги")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerNewBook(@RequestParam Long bookId) {
        libraryService.registerNewBook(bookId);
    }

    @Operation(summary = "Получить все свободные книги")
    @GetMapping("/free")
    public List<RecordResponseDTO> getAvailableBooks() {
        return libraryService.getAvailableBooks();
    }

    @Operation(summary = "Обновить библиотечную запись")
    @PutMapping("/{id}")
    public ResponseEntity<RecordResponseDTO> updateRecord(@PathVariable Long id, @RequestBody RecordRequestDTO recordDTO) {
        RecordResponseDTO updatedRecord = libraryService.updateRecord(id, recordDTO);
        return ResponseEntity.ok().body(updatedRecord);

    }

}