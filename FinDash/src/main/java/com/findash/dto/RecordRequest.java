package com.findash.dto;
import lombok.Data;

@Data
public class RecordRequest {
    private Double amount;
    private String type;
    private String category;
    private String notes;
}