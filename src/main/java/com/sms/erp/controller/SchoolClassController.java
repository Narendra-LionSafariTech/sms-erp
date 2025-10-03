package com.sms.erp.controller;

import com.sms.erp.dto.SchoolClassDTO;
import com.sms.erp.entity.AdminSchool;
import com.sms.erp.entity.SchoolClass;
import com.sms.erp.entity.Staff;
import com.sms.erp.repository.AdminSchoolRepository;
import com.sms.erp.repository.SchoolClassRepository;
import com.sms.erp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/class")
public class SchoolClassController {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private AdminSchoolRepository adminSchoolRepository;

    @Autowired
    private AdminRepository staffRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createSchoolClass(@RequestBody SchoolClassDTO classDTO) {
        try {
            // Validate school
            Optional<AdminSchool> schoolOpt = adminSchoolRepository.findById(classDTO.getSchoolId());
            if (schoolOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("School not found with ID: " + classDTO.getSchoolId());
            }

            Staff teacher = null;
            if (classDTO.getClassTeacherId() != null && !classDTO.getClassTeacherId().isBlank()) {
                Optional<Staff> teacherOpt = staffRepository.findById(classDTO.getClassTeacherId());
                if (teacherOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("Teacher not found with ID: " + classDTO.getClassTeacherId());
                }
                teacher = teacherOpt.get();
            }

            // Create new SchoolClass
            SchoolClass schoolClass = SchoolClass.builder()
                    .school(schoolOpt.get())
                    .className(classDTO.getClassName())
                    .classSection(classDTO.getClassSection())
                    .classStream(classDTO.getClassStream())
                    .classTeacher(teacher)  // may be null
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            SchoolClass savedClass = schoolClassRepository.save(schoolClass);
            return ResponseEntity.ok(savedClass);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating class: " + e.getMessage());
        }
    }


    @GetMapping("/school/{schoolId}")
    public ResponseEntity<?> getAllClassesBySchool(@PathVariable String schoolId) {
        try {
            List<SchoolClass> classes = schoolClassRepository.findBySchoolIdAndIsDeletedFalse(schoolId);
            if (classes.isEmpty()) {
                return ResponseEntity.ok("No classes found for school with ID: " + schoolId);
            }
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching classes: " + e.getMessage());
        }
    }
}
