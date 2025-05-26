
package com.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "trips")
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    @Lob
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
