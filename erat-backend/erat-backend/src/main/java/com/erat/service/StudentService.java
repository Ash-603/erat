package com.erat.service;

import com.erat.model.Student;
import com.erat.model.Class;
import com.erat.repository.StudentRepository;
import com.erat.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.io.IOException;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExcelUtil excelUtil;

    // 导入学生信息
    public List<Student> importFromExcel(MultipartFile file) throws IOException {
        List<Student> students = excelUtil.parseStudentExcel(file);
        return studentRepository.saveAll(students);
    }

    // 获取所有学生
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 按班级获取学生
    public List<Student> getStudentsByClass(Class studentClass) {
        return studentRepository.findByStudentClass(studentClass);
    }

    // 添加学生
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // 删除学生
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    // 根据ID获取学生
    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
