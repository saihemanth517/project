package com.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TripDto {
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
}

