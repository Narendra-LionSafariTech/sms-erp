package com.sms.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "school_classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass {

    @Id
    private String id;

    // Reference to AdminSchool
    @JsonBackReference
    @DBRef
    private AdminSchool school;

    private String className;
    private String classSection;

    // Reference to Staff (Class Teacher)
    @DBRef
    private Staff classTeacher;

    private String classStream;


    private Boolean isDeleted = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
