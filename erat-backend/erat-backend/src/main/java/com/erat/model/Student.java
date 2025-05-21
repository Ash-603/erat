package com.erat.model;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    private String studentId;
    private String name;
    private String grade;
    private String major;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class studentClass;

    // 构造函数
    public Student() {}

    public Student(String studentId, String name, String grade, String major) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.major = major;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }
}
