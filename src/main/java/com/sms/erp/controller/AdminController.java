package com.sms.erp.controller;

import com.sms.erp.dto.LoginRequest;
import com.sms.erp.entity.AdminSchool;
import com.sms.erp.entity.Staff;
import com.sms.erp.repository.AdminRepository;
import com.sms.erp.repository.AdminSchoolRepository;
import com.sms.erp.service.CloudinaryService;
import com.sms.erp.service.IdGeneratorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminRepository adminRepository;
    private final AdminSchoolRepository schoolRepository;
    private final CloudinaryService cloudinaryService;
    private final IdGeneratorService idGeneratorService;
    private final PasswordEncoder passwordEncoder;

    // Create School Admin
    @PostMapping(value = "/create/{schoolId}", consumes = "multipart/form-data")
    public ResponseEntity<?> createSchoolAdmin(
            @PathVariable String schoolId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String mobile,
            @RequestParam String password,
            @RequestParam String address,
            @RequestParam String role,
            @RequestPart(value = "file", required = false) MultipartFile file
//            @RequestAttribute("role") String currentUserRole
    ) {
        // ✅ Temporarily hardcoded role
        String currentUserRole = "super_admin";
        // Only super admin can create new admin
        if (!"super_admin".equalsIgnoreCase(currentUserRole)) {
            return ResponseEntity.status(403).body(Map.of("message", "Only Super Admin allowed to create new admin."));
        }

        // Validate fields
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || mobile.isBlank() || password.isBlank() || address.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Required fields missing"));
        }

        Optional<AdminSchool> existingSchoolOpt = schoolRepository.findById(schoolId);
        if (existingSchoolOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "School not found."));
        }
        AdminSchool existingSchool = existingSchoolOpt.get();

        if (adminRepository.existsByEmailAndSchoolId(email, schoolId)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Admin with this email already exists."));
        }

        String logoUrl = "";
        // If you want to enable Cloudinary upload, uncomment this
        // if (file != null && !file.isEmpty()) {
        //     logoUrl = cloudinaryService.uploadFile(file);
        // }

        String staffRegNo = idGeneratorService.generateStaffId(existingSchool.getSchoolCode());

        Staff newAdmin = Staff.builder()
                .school(existingSchool)
                .schoolCode(existingSchool.getSchoolCode())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .mobile(mobile)
                .address(address)
                .password(passwordEncoder.encode(password))
                .role(role != null ? role : "Admin")
                .avatar(logoUrl)
                .staffRegNo(staffRegNo)
                .permissions(List.of("All", "manage_schools", "view_reports", "manage_system_settings"))
                .build();

        adminRepository.save(newAdmin);

        if (existingSchool.getSchoolAdmins() == null) {
            existingSchool.setSchoolAdmins(new ArrayList<>());
        }
        existingSchool.getSchoolAdmins().add(newAdmin);
        schoolRepository.save(existingSchool);

        return ResponseEntity.status(201).body(Map.of(
                "message", "Admin created successfully.",
                "Admin", Map.of(
                        "id", newAdmin.getId(),
                        "name", newAdmin.getFirstName() + " " + newAdmin.getLastName(),
                        "email", newAdmin.getEmail(),
                        "role", newAdmin.getRole(),
                        "schoolId", schoolId,
                        "staffRegNo", newAdmin.getStaffRegNo()
                )
        ));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest request) {
        Optional<Staff> adminOpt = adminRepository.findByEmail(request.getEmail());
        if (adminOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        Staff admin = adminOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        if (admin.getActive() != null && !admin.getActive()) {
            return ResponseEntity.status(403).body(Map.of("message", "Admin account is deactivated"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "admin", Map.of(
                        "id", admin.getId(),
                        "name", admin.getFirstName() + " " + admin.getLastName(),
                        "email", admin.getEmail(),
                        "role", admin.getRole(),
                        "schoolId", admin.getSchool().getId(),
                        "staffRegNo", admin.getStaffRegNo()
                )
        ));
    }


}
