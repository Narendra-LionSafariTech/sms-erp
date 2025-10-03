package com.sms.erp.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StudentResponseDTO {
    private String id;
    private String studentId;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String rollNumber;

    private String email;
    private String academicYear;
    private String admissionDate; // yyyy-MM-dd
    private String admissionNo;
    private String serialNo;
    private String aadharId;
    private String aparId;
    private String category;
    private String religion;

    private String admissionType;
    private Double feePerMonth;
    private Double admissionFee;
    private Double transportFeePerMonth;

    private String dateOfBirth; // yyyy-MM-dd
    private String gender;
    private String mobile;
    private String address;

    // Related entities as IDs and names (avoid returning full entities)
    private String schoolId;
    private String schoolName;
    private String schoolClassId;
    private String schoolClassName;

    // Embedded objects as DTOs
    private ParentDTO father;
    private ParentDTO mother;
    private GuardianDTO guardian;

    private Boolean active;
    private Boolean isDeleted;
    private Instant createdAt;
    private Instant updatedAt;

    // ---- Nested DTOs ----
    @Data
    public static class ParentDTO {
        private String name;
        private String mobile;
        private String email;
        private String occupation;
        private String avatar;
    }

    @Data
    public static class GuardianDTO {
        private String relations;
        private String name;
        private String email;
        private String mobile;
        private String address;
        private String occupation;
        private String avatar;
    }
}
