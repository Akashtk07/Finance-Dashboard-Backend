package com.findash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String type;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String notes;

    @Column(nullable = false)
    private boolean deleted = false;
}