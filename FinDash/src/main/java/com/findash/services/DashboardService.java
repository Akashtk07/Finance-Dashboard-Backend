package com.findash.services;

import com.findash.model.FinancialRecord;
import com.findash.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    public RecordRepository repo;

    public Map<String, Object> getSummary() {

        List<FinancialRecord> records = repo.findAll();

        double totalIncome = 0;
        double totalExpense = 0;

        for (FinancialRecord r : records) {

            if (r.isDeleted()) continue;

            if ("income".equalsIgnoreCase(r.getType())) {
                totalIncome += r.getAmount();
            } else if ("expense".equalsIgnoreCase(r.getType())) {
                totalExpense += r.getAmount();
            }
        }

        double netBalance = totalIncome - totalExpense;

        Map<String, Object> result = new HashMap<>();
        result.put("totalIncome", totalIncome);
        result.put("totalExpense", totalExpense);
        result.put("netBalance", netBalance);

        return result;
    }


    public Map<String, Object> summary() {
        List<FinancialRecord> list = repo.findAll();
        double income = 0, expense = 0;

        for (FinancialRecord r : list) {
            if ("income".equalsIgnoreCase(r.getType())) income += r.getAmount();
            else expense += r.getAmount();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("totalIncome", income);
        map.put("totalExpense", expense);
        map.put("netBalance", income - expense);
        return map;
    }

    public Map<String, Double> categoryWise() {
        Map<String, Double> map = new HashMap<>();
        for (FinancialRecord r : repo.findAll()) {
            map.put(r.getCategory(), map.getOrDefault(r.getCategory(), 0.0) + r.getAmount());
        }
        return map;
    }

    public List<FinancialRecord> getRecent() {

        return repo.findAll(
                PageRequest.of(0, 5, Sort.by("date").descending())
        ).getContent();
    }
}