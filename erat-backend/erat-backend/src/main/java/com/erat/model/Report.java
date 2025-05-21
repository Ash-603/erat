package com.erat.model;

import javax.persistence.*;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private int expNumber;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String filePath;
    private boolean submitted;

    // 构造函数
    public Report() {}

    public Report(int expNumber, Student student, Course course, String filePath, boolean submitted) {
        this.expNumber = expNumber;
        this.student = student;
        this.course = course;
        this.filePath = filePath;
        this.submitted = submitted;
    }

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public int getExpNumber() {
        return expNumber;
    }

    public void setExpNumber(int expNumber) {
        this.expNumber = expNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }
}