package com.sms.erp.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "staff")
public class Staff {

    @Id
    private String id;

    @Indexed(unique = true)
    private String staffRegNo;

    private String academicYear;
    private String firstName;
    private String middleName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;
    private List<String> specializations;
    private Double basicSalary;
    private Double balance;
    private Double lastIncrement;

    @Builder.Default
    private Date hireDate = new Date();

    @DBRef
    private AdminSchool school;

    private String role;
    private List<String> permissions;

    private String schoolCode;


    @DBRef
    private Staff createdByStaffId;

    private String mobile;
    private String emergencyContact;
    private String gender;  // Could be Enum in Java
    private Date dateOfBirth;
    private String maritalStatus; // Enum if needed
    private String fatherName;
    private String motherName;

    private List<String> qualification;
    private String workExperience;
    private String panNumber;
    private String aadharNumber;
    private String address;
    private String permanentAddress;

    @Builder.Default
    private Boolean active = true;

    private String deviceToken;
    private String token;
    private String avatar;

    private Date lastLogin;
    private Boolean isDeleted;
    private Boolean isOtpVerified;
    private Date otpCreatedAt;
    private String otp;

    private Boolean hasSeenTour;

    // Embedded credentials object
    private Credentials credentials;

    private Boolean isActive;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Credentials {
        private String staffRegNo;
        private String email;
        private String password;
    }
}
