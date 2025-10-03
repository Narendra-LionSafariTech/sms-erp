package com.sms.erp.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String studentId;
    private String role;   // default is "Student", can override if needed

    private String firstName;
    private String middleName;
    private String lastName;
    private String rollNumber;

    private String email;
    private String academicYear;
    private String admissionDate;   // as String, convert to Date in service
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

    private String dateOfBirth;   // keep as string in DTO (format: yyyy-MM-dd)
    private String gender;
    private String mobile;
    private String address;

    // Related entities (references as IDs)
    private String schoolId;
    private String schoolClassId;

    // Embedded objects as DTOs
    private ParentDTO father;
    private ParentDTO mother;
    private GuardianDTO guardian;

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
