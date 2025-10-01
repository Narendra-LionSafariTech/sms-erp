package com.sms.erp.repository;

import com.sms.erp.entity.SchoolClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends MongoRepository<SchoolClass, String> {

    // Find all classes for a specific school
    List<SchoolClass> findBySchoolIdAndIsDeletedFalse(String schoolId);

    // Optional: Find a class by name and section within a school
    SchoolClass findBySchoolIdAndClassNameAndClassSectionAndIsDeletedFalse(
            String schoolId, String className, String classSection
    );
}
