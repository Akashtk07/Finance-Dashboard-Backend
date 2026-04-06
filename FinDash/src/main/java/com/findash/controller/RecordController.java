package com.findash.controller;

import com.findash.dto.ApiResponse;
import com.findash.dto.RecordFilterDTO;
import com.findash.dto.RecordRequestDTO;
import com.findash.model.FinancialRecord;
import com.findash.services.RecordService;
import com.findash.util.RoleValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService service;

    private void check(String role, List<String> allowed) {
        if (!allowed.contains(role)) throw new RuntimeException("Access Denied");
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody RecordRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Record created", service.create(dto))
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<ApiResponse<?>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Fetched",
                        service.getAll(page, size))
        );
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(
            @PathVariable Long id,
            @Valid @RequestBody RecordRequestDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Updated", service.update(id, dto))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Record deleted successfully", null)
        );
    }

    @GetMapping("/filter")
    public List<FinancialRecord> filter(@RequestParam String type, @RequestAttribute String role) {
        check(role, List.of("ADMIN", "ANALYST"));
        return service.filterByType(type);
    }

    @GetMapping("/filter/category")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<ApiResponse<?>> byCategory(
            @RequestParam String category) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Filtered",
                        service.getByCategory(category))
        );
    }

    @GetMapping("/filter/date")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<ApiResponse<?>> byDate(
            @RequestParam String start,
            @RequestParam String end) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Filtered",
                        service.getByDateRange(start, end))
        );
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ResponseEntity<ApiResponse<?>> filter(
            @RequestBody RecordFilterDTO dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Filtered records",
                        service.filterRecords(dto, page, size, sortBy, direction)
                )
        );
    }

}