package com.erat.util;

import com.erat.model.Student;
import com.erat.model.Course;
import com.erat.model.Class;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtil {

    // 解析学生Excel文件
    public List<Student> parseStudentExcel(MultipartFile file) throws IOException {
        List<Student> students = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            // 跳过表头
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    String studentId = getStringCellValue(row.getCell(0));
                    String name = getStringCellValue(row.getCell(1));
                    String grade = getStringCellValue(row.getCell(2));
                    String major = getStringCellValue(row.getCell(3));

                    if (studentId != null && !studentId.isEmpty()) {
                        students.add(new Student(studentId, name, grade, major));
                    }
                }
            }
        }

        return students;
    }

    // 生成学生缺交报告Excel
    public byte[] generateStudentMissingReportsExcel(Map<Student, List<Integer>> data,
                                                     Course course, Class studentClass) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("学生缺交情况");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("学号");
            headerRow.createCell(1).setCellValue("姓名");
            headerRow.createCell(2).setCellValue("专业");
            headerRow.createCell(3).setCellValue("缺交实验列表");

            // 填充数据
            int rowNum = 1;
            for (Map.Entry<Student, List<Integer>> entry : data.entrySet()) {
                Student student = entry.getKey();
                List<Integer> missingExps = entry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(student.getStudentId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getMajor());

                // 将缺交实验列表转换为字符串（如"1,3,5"）
                String missingExpsStr = missingExps.stream()
                        .map(String::valueOf)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                row.createCell(3).setCellValue(missingExpsStr);
            }

            // 调整列宽
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // 生成实验缺交学生Excel
    public byte[] generateExperimentMissingStudentsExcel(Map<Integer, List<Student>> data,
                                                         Course course, Class studentClass) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("实验缺交情况");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("实验编号");
            headerRow.createCell(1).setCellValue("缺交学生列表");

            // 填充数据
            int rowNum = 1;
            for (Map.Entry<Integer, List<Student>> entry : data.entrySet()) {
                Integer expNumber = entry.getKey();
                List<Student> missingStudents = entry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue("实验" + expNumber);

                // 将缺交学生列表转换为字符串（如"张三, 李四, 王五"）
                String missingStudentsStr = missingStudents.stream()
                        .map(Student::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                row.createCell(1).setCellValue(missingStudentsStr);
            }

            // 调整列宽
            for (int i = 0; i < 2; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // 生成提交率Excel
    public byte[] generateSubmissionRatesExcel(Map<Integer, Double> data,
                                               Course course, Class studentClass) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("实验提交率");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("实验编号");
            headerRow.createCell(1).setCellValue("提交率");

            // 填充数据
            int rowNum = 1;
            for (Map.Entry<Integer, Double> entry : data.entrySet()) {
                Integer expNumber = entry.getKey();
                Double rate = entry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue("实验" + expNumber);
                row.createCell(1).setCellValue(String.format("%.2f%%", rate * 100));
            }

            // 调整列宽
            for (int i = 0; i < 2; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // 获取单元格字符串值
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
