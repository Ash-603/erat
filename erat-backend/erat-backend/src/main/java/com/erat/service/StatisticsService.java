package com.erat.service;

import com.erat.model.Report;
import com.erat.model.Student;
import com.erat.model.Course;
import com.erat.model.Class;
import com.erat.repository.ReportRepository;
import com.erat.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReportService reportService;

    // 按学生统计缺交情况
    public Map<Student, List<Integer>> analyzeByStudent(Course course, Class studentClass) {
        List<Student> students = studentRepository.findByStudentClass(studentClass);
        Map<Student, List<Integer>> result = new HashMap<>();

        for (Student student : students) {
            List<Report> reports = reportService.getReportsByStudentAndCourse(student, course);
            List<Integer> missingExps = new ArrayList<>();

            // 检查每个实验的提交情况
            for (int i = 1; i <= course.getExpCount(); i++) {
                final int expNum = i;
                boolean submitted = reports.stream()
                        .anyMatch(r -> r.getExpNumber() == expNum && r.isSubmitted());

                if (!submitted) {
                    missingExps.add(i);
                }
            }

            if (!missingExps.isEmpty()) {
                result.put(student, missingExps);
            }
        }

        return result;
    }

    // 按实验统计缺交情况
    public Map<Integer, List<Student>> analyzeByExperiment(Course course, Class studentClass) {
        List<Student> students = studentRepository.findByStudentClass(studentClass);
        Map<Integer, List<Student>> result = new HashMap<>();

        // 初始化每个实验的缺交学生列表
        for (int i = 1; i <= course.getExpCount(); i++) {
            result.put(i, new ArrayList<>());
        }

        for (Student student : students) {
            List<Report> reports = reportService.getReportsByStudentAndCourse(student, course);

            // 检查每个实验的提交情况
            for (int i = 1; i <= course.getExpCount(); i++) {
                final int expNum = i;
                boolean submitted = reports.stream()
                        .anyMatch(r -> r.getExpNumber() == expNum && r.isSubmitted());

                if (!submitted) {
                    result.get(i).add(student);
                }
            }
        }

        // 移除没有缺交学生的实验
        result.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        return result;
    }

    // 计算实验提交率
    public Map<Integer, Double> calculateSubmissionRates(Course course, Class studentClass) {
        List<Student> students = studentRepository.findByStudentClass(studentClass);
        int totalStudents = students.size();
        Map<Integer, Double> submissionRates = new HashMap<>();

        for (int i = 1; i <= course.getExpCount(); i++) {
            final int expNum = i;
            List<Report> reports = reportService.getReportsByExpNumberAndCourse(expNum, course);

            // 计算提交人数
            long submittedCount = reports.stream()
                    .filter(Report::isSubmitted)
                    .count();

            // 计算提交率
            double rate = (double) submittedCount / totalStudents;
            submissionRates.put(expNum, rate);
        }

        return submissionRates;
    }
}