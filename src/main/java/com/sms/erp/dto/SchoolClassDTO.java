package com.sms.erp.dto;

import lombok.Data;

@Data
public class SchoolClassDTO {
    private String schoolId;
    private String className;
    private String classSection;
    private String classStream;
    private String classTeacherId; // staff id
}
