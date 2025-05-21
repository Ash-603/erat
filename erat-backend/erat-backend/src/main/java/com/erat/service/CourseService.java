package com.erat.service;

import com.erat.model.Course;
import com.erat.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // 获取所有课程
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 添加课程
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // 删除课程
    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
    }

    // 根据ID获取课程
    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
}
