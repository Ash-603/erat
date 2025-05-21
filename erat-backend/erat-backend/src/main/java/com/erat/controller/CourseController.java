package com.erat.controller;

import com.erat.model.Course;
import com.erat.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // 获取所有课程
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // 获取指定课程
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }

    // 添加课程
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(course));
    }

    // 更新课程
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @RequestBody Course course) {
        if (!courseId.equals(course.getCourseId())) {
            return ResponseEntity.badRequest().build();
        }

        if (courseService.getCourseById(courseId) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(courseService.addCourse(course));
    }

    // 删除课程
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseId) {
        if (courseService.getCourseById(courseId) == null) {
            return ResponseEntity.notFound().build();
        }

        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
