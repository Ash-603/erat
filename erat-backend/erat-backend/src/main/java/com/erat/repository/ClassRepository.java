package com.erat.repository;

import com.erat.model.Class;
import com.erat.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    List<Class> findByMajor(String major);
    List<Class> findByGrade(String grade);
}

