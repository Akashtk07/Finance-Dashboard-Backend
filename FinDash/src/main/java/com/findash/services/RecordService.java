package com.findash.services;


import com.findash.dto.RecordFilterDTO;
import com.findash.dto.RecordRequestDTO;
import com.findash.model.FinancialRecord;
import com.findash.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
@Service
public class RecordService {

    @Autowired
    private RecordRepository repo;

    public FinancialRecord create(RecordRequestDTO dto) {

        FinancialRecord record = new FinancialRecord();

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setNotes(dto.getNotes());
        record.setDate(LocalDate.parse(dto.getDate()));


        return repo.save(record);
    }

    public List<FinancialRecord> getAll() {
        return repo.findAll();
    }


    public Page<FinancialRecord> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repo.findAll(pageable);
    }

    public FinancialRecord getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public FinancialRecord update(Long id, RecordRequestDTO dto) {

        FinancialRecord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));


        existing.setAmount(dto.getAmount());
        existing.setType(dto.getType());
        existing.setCategory(dto.getCategory());
        existing.setNotes(dto.getNotes());
        existing.setDate(LocalDate.parse(dto.getDate()));

        return repo.save(existing);
    }
//    public FinancialRecord update(Long id, FinancialRecord r) {
//        FinancialRecord existing = getById(id);
//        existing.setAmount(r.getAmount());
//        existing.setCategory(r.getCategory());
//        existing.setType(r.getType());
//        return repo.save(existing);
//    }

    public void delete(Long id) {
        repo.deleteById(id);
    }



    public List<FinancialRecord> filterByType(String type) {
        return repo.findByType(type);
    }

    public List<FinancialRecord> getByCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new RuntimeException("Category is required");
        }

        return repo.findByCategory(category);
    }

    public List<FinancialRecord> getByDateRange(String start, String end) {

        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            if (startDate.isAfter(endDate)) {
                throw new RuntimeException("Start date cannot be after end date");
            }

            return repo.findByDateBetween(startDate, endDate);

        } catch (Exception e) {
            throw new RuntimeException("Invalid date format. Use YYYY-MM-DD");
        }
    }


    public Page<FinancialRecord> filterRecords(
            RecordFilterDTO dto,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(
                RecordSpecification.filter(
                        dto.getType(),
                        dto.getCategory(),
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getKeyword()
                ),
                pageable
        );
    }
}