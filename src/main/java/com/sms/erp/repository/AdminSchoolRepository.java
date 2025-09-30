package com.sms.erp.repository;


import com.sms.erp.entity.AdminSchool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminSchoolRepository extends MongoRepository<AdminSchool, String> {
}
