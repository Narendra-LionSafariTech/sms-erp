package com.sms.erp.controller;


import com.sms.erp.entity.AdminSchool;
import com.sms.erp.repository.AdminSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SuperAdmin {

    private final AdminSchoolRepository adminSchoolRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createSchool(@RequestBody AdminSchool school) {
        try {
            // Set default values if not provided
            if (school.getRegistrationDate() == null) {
                school.setRegistrationDate(new Date());
            }
            if (school.getRole() == null) {
                school.setRole("school_admin");
            }
            if (school.getActive() == null) {
                school.setActive(true);
            }

            AdminSchool savedSchool = adminSchoolRepository.save(school);
            return ResponseEntity.ok(savedSchool);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating school: " + e.getMessage());
        }
    }
}

