package com.erat.repository;

import com.erat.model.Report;
import com.erat.model.Student;
import com.erat.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStudentAndCourse(Student student, Course course);
    List<Report> findByExpNumberAndCourse(int expNumber, Course course);
    List<Report> findByCourse(Course course);
    List<Report> findByStudent(Student student);
}
