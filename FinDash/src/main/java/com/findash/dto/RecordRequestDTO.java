package com.findash.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RecordRequestDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Type is required")
    private String type;  // income / expense

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Date is required")
    private String date;  // yyyy-MM-dd

    private String notes;
}