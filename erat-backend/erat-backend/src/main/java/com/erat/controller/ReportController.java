package com.erat.controller;

import com.erat.model.Report;
import com.erat.model.Course;
import com.erat.model.Class;
import com.erat.service.ReportService;
import com.erat.service.CourseService;
import com.erat.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    // 扫描实验报告目录
    @PostMapping("/scan")
    public ResponseEntity<List<Report>> scanReportDirectory(
            @RequestParam String courseId,
            @RequestParam String classId) {

        Course course = courseService.getCourseById(courseId);
        Class studentClass = classService.getClassById(classId);

        if (course == null || studentClass == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Report> reports = reportService.scanReportDirectory(course, studentClass);
        return ResponseEntity.ok(reports);
    }

    // 获取指定课程的所有报告
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Report>> getReportsByCourse(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reportService.getReportsByCourse(course));
    }

    // 获取指定课程的指定实验报告
    @GetMapping("/course/{courseId}/experiment/{expNumber}")
    public ResponseEntity<List<Report>> getReportsByExpNumberAndCourse(
            @PathVariable String courseId,
            @PathVariable int expNumber) {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reportService.getReportsByExpNumberAndCourse(expNumber, course));
    }
}