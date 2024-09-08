package com.example.library_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordDTO {

    private Long id;

    private Long bookId;

    private LocalDateTime borrowedAt;

    private LocalDateTime returnBy;

}
