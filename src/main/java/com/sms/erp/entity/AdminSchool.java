package com.sms.erp.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "admin_schools")
public class AdminSchool {

    @Id
    private String id;

    @Indexed(unique = true)
    private String adminSchoolId;

    @Indexed(unique = true)
    private String schoolCode;

    private String schoolName;
    private String affiliation;
    private String educationBoard;
    private String website;

    private String email;
    private String academicYear;
    private String mobile;
    // private String password; // Uncomment if password is required
    private String address;
    private String alternateMobile;

    @Builder.Default
    private Date registrationDate = new Date();

    @Builder.Default
    private String role = "school_admin";

    private String desc;
    private List<String> permissions;

    private Date trialExpiryDate;
    private Date subscriptionExpiryDate;
    private Date subscriptionStartDate;

    private SchoolDetails schoolDetails;

    @Builder.Default
    private Boolean active = true;

    private String deviceToken;
    private String token;
    private String logo;

    private Date lastLogin;
    private Boolean isDeleted;
    private Boolean isOtpVerified;
    private Date otpCreatedAt;
    private String otp;
    // in AdminSchool.java
    @DBRef
    private List<Staff> schoolAdmins = new ArrayList<>();


    @Builder.Default
    private Boolean hasSeenTour = false;

    @Builder.Default
    private Boolean isCreditApplicable = false;

    private Credit credit;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    // ==========================
    // Embedded Sub-Documents
    // ==========================

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchoolDetails {
        private Integer numberOfStudents = 0;
        private Integer numberOfTeachers = 0;
        private Integer numberOfClasses = 0;
        private Integer numberOfStaffs = 0;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Credit {
        private Double creditLimit = 0.0;
        private Double utilized = 0.0;
        private Double remaining;
        private List<Transaction> transactions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transaction {
        private Double amount;
        private String type; // debit or credit
        private String description;
        private Date date = new Date();
    }
}
