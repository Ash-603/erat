package com.erat.service;

import com.erat.model.Student;
import com.erat.model.Course;
import com.erat.model.Class;
import com.erat.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ExcelUtil excelUtil;

    // 导出学生缺交情况
    public byte[] exportStudentMissingReports(Course course, Class studentClass) throws IOException {
        Map<Student, List<Integer>> data = statisticsService.analyzeByStudent(course, studentClass);
        return excelUtil.generateStudentMissingReportsExcel(data, course, studentClass);
    }

    // 导出实验缺交情况
    public byte[] exportExperimentMissingStudents(Course course, Class studentClass) throws IOException {
        Map<Integer, List<Student>> data = statisticsService.analyzeByExperiment(course, studentClass);
        return excelUtil.generateExperimentMissingStudentsExcel(data, course, studentClass);
    }

    // 导出提交率数据
    public byte[] exportSubmissionRates(Course course, Class studentClass) throws IOException {
        Map<Integer, Double> data = statisticsService.calculateSubmissionRates(course, studentClass);
        return excelUtil.generateSubmissionRatesExcel(data, course, studentClass);
    }
}


