package com.sms.erp.repository;


import com.sms.erp.entity.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Staff, String> {
    boolean existsByEmailAndSchoolId(String email, String schoolId);
}

