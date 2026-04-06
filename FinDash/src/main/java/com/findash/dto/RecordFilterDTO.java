package com.findash.dto;


import lombok.Data;

@Data
public class RecordFilterDTO {

    private String type;        // income / expense
    private String category;    // salary, food, etc.
    private String startDate;   // yyyy-MM-dd
    private String endDate;
    private String keyword;
}