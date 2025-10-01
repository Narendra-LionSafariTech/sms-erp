package com.sms.erp.controller;

import com.sms.erp.entity.Student;
import com.sms.erp.repository.StudentRepository;
import com.sms.erp.repository.AdminSchoolRepository;
import com.sms.erp.repository.SchoolClassRepository;


import com.sms.erp.entity.AdminSchool;
import com.sms.erp.entity.SchoolClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminSchoolRepository schoolRepository; // Make sure you have this
    @Autowired
    private SchoolClassRepository schoolClassRepository; // Make sure you have this

    // ================= Create / Onboard Student =================
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            // Validate required fields
            if (student.getFirstName() == null || student.getAcademicYear() == null) {
                return ResponseEntity.badRequest().body("First name and academic year are required");
            }

            // Check if studentId already exists for the same academic year
            Optional<Student> existing = studentRepository
                    .findByStudentIdAndAcademicYear(student.getStudentId(), student.getAcademicYear());
            if (existing.isPresent()) {
                return ResponseEntity.badRequest().body("Student with this ID already exists for the academic year");
            }

            // Validate school
            if (student.getSchool() != null) {
                Optional<AdminSchool> schoolOpt = schoolRepository.findById(student.getSchool().getId());
                if (schoolOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("Invalid school ID");
                }
                student.setSchool(schoolOpt.get());
            }

            // Validate school class
            if (student.getSchoolClass() != null) {
                Optional<SchoolClass> classOpt = schoolClassRepository.findById(student.getSchoolClass().getId());
                if (classOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("Invalid class ID");
                }
                student.setSchoolClass(classOpt.get());
            }

            student.setCreatedAt(Instant.now());
            student.setActive(true);
            student.setIsDeleted(false);

            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.ok(savedStudent);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating student: " + e.getMessage());
        }
    }
}
