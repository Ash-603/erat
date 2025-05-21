package com.erat.service;

import com.erat.model.Report;
import com.erat.model.Student;
import com.erat.model.Course;
import com.erat.model.Class;
import com.erat.repository.ReportRepository;
import com.erat.repository.StudentRepository;
import com.erat.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileUtil fileUtil;

    // 扫描实验报告目录
    public List<Report> scanReportDirectory(Course course, Class studentClass) {
        List<Report> reports = new ArrayList<>();
        List<Student> students = studentRepository.findByStudentClass(studentClass);

        try {
            String directoryPath = course.getPath() + "/" + studentClass.getName() + "/";
            File directory = new File(directoryPath);

            if (!directory.exists() || !directory.isDirectory()) {
                throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
            }

            // 遍历目录下的所有文件
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        Report report = parseReportFile(path, course, students);
                        if (report != null) {
                            reports.add(report);
                        }
                    });

            // 对于每个学生的每个实验，如果没有找到对应的报告，则创建一个未提交的记录
            for (Student student : students) {
                for (int i = 1; i <= course.getExpCount(); i++) {
                    boolean found = false;
                    for (Report report : reports) {
                        if (report.getStudent().getStudentId().equals(student.getStudentId())
                                && report.getExpNumber() == i) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Report missingReport = new Report(i, student, course, null, false);
                        reports.add(missingReport);
                    }
                }
            }

            return reportRepository.saveAll(reports);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 从文件路径解析报告信息
    private Report parseReportFile(Path path, Course course, List<Student> students) {
        try {
            String fileName = path.getFileName().toString();

            // 假设文件名格式为："实验{num}_{studentId}_{name}.{ext}"
            Pattern pattern = Pattern.compile("实验(\\d+)_(\\d+)_.*\\..+");
            Matcher matcher = pattern.matcher(fileName);

            if (matcher.matches()) {
                int expNumber = Integer.parseInt(matcher.group(1));
                String studentId = matcher.group(2);

                // 查找对应的学生
                Student student = null;
                for (Student s : students) {
                    if (s.getStudentId().equals(studentId)) {
                        student = s;
                        break;
                    }
                }

                if (student != null && expNumber <= course.getExpCount()) {
                    return new Report(expNumber, student, course, path.toString(), true);
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取指定课程的所有报告
    public List<Report> getReportsByCourse(Course course) {
        return reportRepository.findByCourse(course);
    }

    // 获取指定学生的所有报告
    public List<Report> getReportsByStudent(Student student) {
        return reportRepository.findByStudent(student);
    }

    // 获取指定学生在指定课程的所有报告
    public List<Report> getReportsByStudentAndCourse(Student student, Course course) {
        return reportRepository.findByStudentAndCourse(student, course);
    }

    // 获取指定课程的指定实验报告
    public List<Report> getReportsByExpNumberAndCourse(int expNumber, Course course) {
        return reportRepository.findByExpNumberAndCourse(expNumber, course);
    }
}