package com.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TripResponseDto {
    private Long id;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private String username; // Optional: show who created it
}

