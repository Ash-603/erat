package com.erat.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Class {
    @Id
    private String classId;
    private String name;
    private String grade;
    private String major;

    @OneToMany(mappedBy = "studentClass")
    private List<Student> students;

    // 构造函数
    public Class() {}

    public Class(String classId, String name, String grade, String major) {
        this.classId = classId;
        this.name = name;
        this.grade = grade;
        this.major = major;
    }

    // Getters and Setters
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}