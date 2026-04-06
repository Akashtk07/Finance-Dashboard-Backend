package com.findash.services;

import com.findash.model.FinancialRecord;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;



import com.findash.model.FinancialRecord;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RecordSpecification {

    public static Specification<FinancialRecord> filter(
            String type,
            String category,
            String startDate,
            String endDate,
            String keyword
    ) {

        return (root, query, cb) -> {

            var predicates = cb.conjunction();


            if (type != null && !type.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("type"), type));
            }


            if (category != null && !category.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("category"), category));
            }


            if (startDate != null && endDate != null) {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);

                predicates = cb.and(predicates,
                        cb.between(root.get("date"), start, end));
            }


            if (keyword != null && !keyword.isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(root.get("category"), "%" + keyword + "%"));
            }


            predicates = cb.and(predicates,
                    cb.equal(root.get("deleted"), false));

            return predicates;
        };
    }
}