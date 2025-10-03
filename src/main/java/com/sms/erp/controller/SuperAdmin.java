package com.sms.erp.controller;


import com.sms.erp.entity.AdminSchool;
import com.sms.erp.repository.AdminSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SuperAdmin {

    @Autowired
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllSchools() {
        try {
            List<AdminSchool> schools = adminSchoolRepository.findAll();
            if (((List<?>) schools).isEmpty()) {
                return ResponseEntity.ok("No schools found");
            }
            return ResponseEntity.ok(schools);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching schools: " + e.getMessage());
        }
    }
}

