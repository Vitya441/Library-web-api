package com.example.library_service.controller;

import com.example.library_service.dto.RecordDTO;
import com.example.library_service.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/library") //todo: Изменил на /api/v1/library на /library
public class LibraryController {

    private final LibraryService libraryService;

    @Operation(summary = "Регистрация книги в библиотеке", description = "Данный эндпоинт вызывается сервисом BookService при создании новой книги")
    @PostMapping("/register")
    public ResponseEntity<Void> registerNewBook(@RequestParam Long bookId) {
        libraryService.registerNewBook(bookId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получить все свободные книги")
    @GetMapping("/free")
    public List<RecordDTO> getAvailableBooks() {
        return libraryService.getAvailableBooks();
    }

    @Operation(summary = "Обновить библиотечную запись")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable Long id, @RequestBody RecordDTO newRecord) {
        RecordDTO updatedRecord = libraryService.updateRecord(id, newRecord);
        return ResponseEntity.ok().body(updatedRecord);

    }

}