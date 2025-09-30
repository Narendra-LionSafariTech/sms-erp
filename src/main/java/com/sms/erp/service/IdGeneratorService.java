package com.sms.erp.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGeneratorService {
    public String generateStaffId(String schoolCode) {
        return schoolCode + "-STAFF-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
