package com.erat.controller;

import com.erat.model.Student;
import com.erat.model.Class;
import com.erat.service.StudentService;
import com.erat.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    // 获取所有学生
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // 获取指定班级的学生
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable String classId) {
        Class studentClass = classService.getClassById(classId);
        if (studentClass == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentsByClass(studentClass));
    }

    // 获取指定学生
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable String studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    // 添加学生
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(student));
    }

    // 更新学生
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String studentId, @RequestBody Student student) {
        if (!studentId.equals(student.getStudentId())) {
            return ResponseEntity.badRequest().build();
        }

        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // 删除学生
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    // 从Excel导入学生
    @PostMapping("/import")
    public ResponseEntity<List<Student>> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> students = studentService.importFromExcel(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(students);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
