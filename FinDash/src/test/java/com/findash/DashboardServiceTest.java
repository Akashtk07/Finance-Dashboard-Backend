package com.findash;


import com.findash.model.FinancialRecord;
import com.findash.repository.RecordRepository;
import com.findash.services.DashboardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardServiceTest {

    @Test
    void testSummaryCalculation() {

        RecordRepository repo = Mockito.mock(RecordRepository.class);

        FinancialRecord r1 = new FinancialRecord();
        r1.setAmount(1000.0);
        r1.setType("income");

        FinancialRecord r2 = new FinancialRecord();
        r2.setAmount(500.0);
        r2.setType("expense");

        Mockito.when(repo.findAll()).thenReturn(List.of(r1, r2));

        DashboardService service = new DashboardService();
        service.repo = repo;

        var result = service.summary();

        assertEquals(1000.0, result.get("totalIncome"));
        assertEquals(500.0, result.get("totalExpense"));
        assertEquals(500.0, result.get("netBalance"));
    }
}