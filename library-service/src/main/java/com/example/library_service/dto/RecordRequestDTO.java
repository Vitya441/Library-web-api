package com.example.library_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordRequestDTO {

    private LocalDateTime borrowedAt;

    private LocalDateTime returnBy;
}
