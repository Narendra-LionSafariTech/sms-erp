package com.sms.erp.entity;


import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
@CompoundIndexes({
        @CompoundIndex(name = "studentId_academicYear_idx", def = "{'studentId': 1, 'academicYear': 1}"),
        @CompoundIndex(name = "school_isDeleted_idx", def = "{'school': 1, 'isDeleted': 1}")
})
public class Student {

    @Id
    private String id;

    private String studentId;
    @Builder.Default
    private String role = "Student";

    private String firstName;
    private String middleName;
    private String lastName;

    private String rollNumber;
    private String email;
    private String academicYear;
    private Date admissionDate;
    private Date leaveDate;
    private String leaveReason;
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


    @DBRef
    private SchoolClass schoolClass;

    @DBRef
    private AdminSchool school;

    private Date dateOfBirth;
    private String gender; // Enum can be defined if you want stronger typing
    private String mobile;
    private String address;

    @Builder.Default
    private Boolean active = true;
    private String deviceToken;
    private String token;
    private String avatar;
    private Date lastLogin;
    private Boolean isDeleted;
    private Date deletedAt;

    private Boolean isOtpVerified;
    private Date otpCreatedAt;
    private String otp;
    private Boolean hasSeenTour;

    private Boolean isParentDetailsFilled;
    private Boolean isFeeDetailsFilled;
    private Boolean isTransportDetailsFilled;

    // ========== Embedded Documents ==========
    private Parent father;
    private Parent mother;
    private Guardian guardian;

    // Timestamps
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    // ========== Embedded Classes ==========
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parent {
        private String name;
        private String mobile;
        private String email;
        private String occupation;
        private String avatar;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Guardian {
        private String relations;
        private String name;
        private String email;
        private String mobile;
        private String address;
        private String occupation;
        private String avatar;
    }
}

