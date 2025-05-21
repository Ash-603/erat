package com.erat.service;

import com.erat.model.Class;
import com.erat.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    // 获取所有班级
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    // 添加班级
    public Class addClass(Class studentClass) {
        return classRepository.save(studentClass);
    }

    // 删除班级
    public void deleteClass(String classId) {
        classRepository.deleteById(classId);
    }

    // 根据ID获取班级
    public Class getClassById(String classId) {
        return classRepository.findById(classId).orElse(null);
    }
}