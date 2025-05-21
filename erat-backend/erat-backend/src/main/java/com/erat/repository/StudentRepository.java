package com.erat.repository;

import com.erat.model.Student;
import com.erat.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByStudentClass(Class studentClass);
    List<Student> findByMajor(String major);
    List<Student> findByGrade(String grade);
}
