package com.findash;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.findash.controller.RecordController;
import com.findash.model.FinancialRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecordController.class)
public class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreateRecord_AsAdmin() throws Exception {
        FinancialRecord record = new FinancialRecord();
        record.setAmount(1000.0);
        record.setType("income");
        record.setDate(LocalDate.now());

        mockMvc.perform(post("/records")
                        .header("role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(record)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateRecord_AsViewer_ShouldFail() throws Exception {
        FinancialRecord record = new FinancialRecord();

        mockMvc.perform(post("/records")
                        .header("role", "VIEWER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(record)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetRecords_AsAnalyst() throws Exception {
        mockMvc.perform(get("/records")
                        .header("role", "ANALYST"))
                .andExpect(status().isOk());
    }
}