package com.erat.controller;

import com.erat.model.Class;
import com.erat.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    // 获取所有班级
    @GetMapping
    public ResponseEntity<List<Class>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    // 获取指定班级
    @GetMapping("/{classId}")
    public ResponseEntity<Class> getClassById(@PathVariable String classId) {
        Class studentClass = classService.getClassById(classId);
        if (studentClass == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentClass);
    }

    // 添加班级
    @PostMapping
    public ResponseEntity<Class> addClass(@RequestBody Class studentClass) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classService.addClass(studentClass));
    }

    // 更新班级
    @PutMapping("/{classId}")
    public ResponseEntity<Class> updateClass(@PathVariable String classId, @RequestBody Class studentClass) {
        if (!classId.equals(studentClass.getClassId())) {
            return ResponseEntity.badRequest().build();
        }

        if (classService.getClassById(classId) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(classService.addClass(studentClass));
    }

    // 删除班级
    @DeleteMapping("/{classId}")
    public ResponseEntity<Void> deleteClass(@PathVariable String classId) {
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.notFound().build();
        }

        classService.deleteClass(classId);
        return ResponseEntity.noContent().build();
    }
}
