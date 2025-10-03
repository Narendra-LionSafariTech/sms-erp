package com.sms.erp.controller;

import com.sms.erp.dto.StudentDTO;
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
@RequestMapping("/api/v1.0/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminSchoolRepository schoolRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    // ================= Create / Onboard Student =================
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO dto) {
        try {
            // ===== Validate School =====
            Optional<AdminSchool> schoolOpt = schoolRepository.findById(dto.getSchoolId());
            if (schoolOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid school ID");
            }

            // ===== Validate Class =====
            Optional<SchoolClass> classOpt = schoolClassRepository.findById(dto.getSchoolClassId());
            if (classOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid class ID");
            }

            // ===== Map DTO → Entity =====
            Student student = new Student();
            student.setStudentId(dto.getStudentId());
            student.setRole(dto.getRole() != null ? dto.getRole() : "Student");
            student.setFirstName(dto.getFirstName());
            student.setMiddleName(dto.getMiddleName());
            student.setLastName(dto.getLastName());
            student.setRollNumber(dto.getRollNumber());
            student.setEmail(dto.getEmail());
            student.setAcademicYear(dto.getAcademicYear());
            student.setAdmissionNo(dto.getAdmissionNo());
            student.setSerialNo(dto.getSerialNo());
            student.setAadharId(dto.getAadharId());
            student.setAparId(dto.getAparId());
            student.setCategory(dto.getCategory());
            student.setReligion(dto.getReligion());
            student.setAdmissionType(dto.getAdmissionType());
            student.setFeePerMonth(dto.getFeePerMonth());
            student.setAdmissionFee(dto.getAdmissionFee());
            student.setTransportFeePerMonth(dto.getTransportFeePerMonth());
            student.setGender(dto.getGender());
            student.setMobile(dto.getMobile());
            student.setAddress(dto.getAddress());

            // ===== Convert Dates =====
            if (dto.getDateOfBirth() != null) {
                student.setDateOfBirth(java.sql.Date.valueOf(dto.getDateOfBirth())); // expects yyyy-MM-dd
            }
            if (dto.getAdmissionDate() != null) {
                student.setAdmissionDate(java.sql.Date.valueOf(dto.getAdmissionDate()));
            }

            // ===== Set References =====
            student.setSchool(schoolOpt.get());
            student.setSchoolClass(classOpt.get());

            // ===== Embedded Parents =====
            if (dto.getFather() != null) {
                student.setFather(new Student.Parent(
                        dto.getFather().getName(),
                        dto.getFather().getMobile(),
                        dto.getFather().getEmail(),
                        dto.getFather().getOccupation(),
                        dto.getFather().getAvatar()
                ));
            }

            if (dto.getMother() != null) {
                student.setMother(new Student.Parent(
                        dto.getMother().getName(),
                        dto.getMother().getMobile(),
                        dto.getMother().getEmail(),
                        dto.getMother().getOccupation(),
                        dto.getMother().getAvatar()
                ));
            }

            if (dto.getGuardian() != null) {
                student.setGuardian(new Student.Guardian(
                        dto.getGuardian().getRelations(),
                        dto.getGuardian().getName(),
                        dto.getGuardian().getEmail(),
                        dto.getGuardian().getMobile(),
                        dto.getGuardian().getAddress(),
                        dto.getGuardian().getOccupation(),
                        dto.getGuardian().getAvatar()
                ));
            }

            // ===== System Fields =====
            student.setCreatedAt(Instant.now());
            student.setActive(true);
            student.setIsDeleted(false);

            // ===== Save & Return =====
            Student saved = studentRepository.save(student);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating student: " + e.getMessage());
        }
    }

}
