package com.findash.config;


import com.findash.model.*;
import com.findash.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepo,
            RecordRepository recordRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {


            if (userRepo.count() > 0) {
                return;
            }



            User admin = new User();
            admin.setName("Akash Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPhone("9876543210");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            User analyst = new User();
            analyst.setName("Rahul Analyst");
            analyst.setEmail("analyst@gmail.com");
            analyst.setPhone("9876543211");
            analyst.setPassword(passwordEncoder.encode("123456"));
            analyst.setRole(Role.ANALYST);
            analyst.setActive(true);

            User viewer = new User();
            viewer.setName("Priya Viewer");
            viewer.setEmail("viewer@gmail.com");
            viewer.setPhone("9876543212");
            viewer.setPassword(passwordEncoder.encode("123456"));
            viewer.setRole(Role.VIEWER);
            viewer.setActive(true);

            userRepo.save(admin);
            userRepo.save(analyst);
            userRepo.save(viewer);



            recordRepo.save(createRecord(50000, "income", "salary",
                    "January salary", "2026-01-10"));

            recordRepo.save(createRecord(5000, "expense", "food",
                    "Dining out", "2026-01-12"));

            recordRepo.save(createRecord(20000, "income", "freelance",
                    "Freelance project", "2026-02-05"));

            recordRepo.save(createRecord(10000, "expense", "rent",
                    "House rent", "2026-02-01"));

            recordRepo.save(createRecord(3000, "expense", "travel",
                    "Cab expenses", "2026-03-02"));

            recordRepo.save(createRecord(15000, "income", "bonus",
                    "Performance bonus", "2026-03-15"));

            System.out.println("Sample data loaded successfully!");
        };
    }


    private FinancialRecord createRecord(
            double amount,
            String type,
            String category,
            String notes,
            String date) {

        FinancialRecord record = new FinancialRecord();
        record.setAmount(amount);
        record.setType(type);
        record.setCategory(category);
        record.setNotes(notes);
        record.setDate(LocalDate.parse(date));


        return record;
    }
}