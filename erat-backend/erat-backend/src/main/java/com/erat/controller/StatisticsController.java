package com.erat.controller;

import com.erat.model.Student;
import com.erat.model.Course;
import com.erat.model.Class;
import com.erat.service.StatisticsService;
import com.erat.service.CourseService;
import com.erat.service.ClassService;
import com.erat.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ExportService exportService;

    // 按学生统计缺交情况
    @GetMapping("/student")
    public ResponseEntity<Map<Student, List<Integer>>> analyzeByStudent(
            @RequestParam String courseId,
            @RequestParam String classId) {

        Course course = courseService.getCourseById(courseId);
        Class studentClass = classService.getClassById(classId);

        if (course == null || studentClass == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<Student, List<Integer>> result = statisticsService.analyzeByStudent(course, studentClass);
        return ResponseEntity.ok(result);
    }

    // 按实验统计缺交情况
    @GetMapping("/experiment")
    public ResponseEntity<Map<Integer, List<Student>>> analyzeByExperiment(
            @RequestParam String courseId,
            @RequestParam String classId) {

        Course course = courseService.getCourseById(courseId);
        Class studentClass = classService.getClassById(classId);

        if (course == null || studentClass == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<Integer, List<Student>> result = statisticsService.analyzeByExperiment(course, studentClass);
        return ResponseEntity.ok(result);
    }

    // 计算实验提交率
    @GetMapping("/submission-rates")
    public ResponseEntity<Map<Integer, Double>> calculateSubmissionRates(
            @RequestParam String courseId,
            @RequestParam String classId) {

        Course course = courseService.getCourseById(courseId);
        Class studentClass = classService.getClassById(classId);

        if (course == null || studentClass == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<Integer, Double> result = statisticsService.calculateSubmissionRates(course, studentClass);
        return ResponseEntity.ok(result);
    }

    // 导出学生缺交情况
    @GetMapping("/export/student")
    public ResponseEntity<byte[]> exportStudentMissingReports(
            @RequestParam String courseId,
            @RequestParam String classId) {

        try {
            Course course = courseService.getCourseById(courseId);
            Class studentClass = classService.getClassById(classId);

            if (course == null || studentClass == null) {
                return ResponseEntity.badRequest().build();
            }

            byte[] excelBytes = exportService.exportStudentMissingReports(course, studentClass);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "学生缺交情况.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 导出实验缺交情况
    @GetMapping("/export/experiment")
    public ResponseEntity<byte[]> exportExperimentMissingStudents(
            @RequestParam String courseId,
            @RequestParam String classId) {

        try {
            Course course = courseService.getCourseById(courseId);
            Class studentClass = classService.getClassById(classId);

            if (course == null || studentClass == null) {
                return ResponseEntity.badRequest().build();
            }

            byte[] excelBytes = exportService.exportExperimentMissingStudents(course, studentClass);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "实验缺交情况.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 导出提交率数据
    @GetMapping("/export/submission-rates")
    public ResponseEntity<byte[]> exportSubmissionRates(
            @RequestParam String courseId,
            @RequestParam String classId) {

        try {
            Course course = courseService.getCourseById(courseId);
            Class studentClass = classService.getClassById(classId);

            if (course == null || studentClass == null) {
                return ResponseEntity.badRequest().build();
            }

            byte[] excelBytes = exportService.exportSubmissionRates(course, studentClass);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "实验提交率.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}