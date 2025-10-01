package com.sms.erp.repository;

import com.sms.erp.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByStudentIdAndAcademicYear(String studentId, String academicYear);
}

