package com.erat.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    private String courseId;
    private String name;
    private int expCount;
    private String path;

    @ManyToMany
    @JoinTable(
            name = "course_class",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<Class> classes;

    // 构造函数
    public Course() {}

    public Course(String courseId, String name, int expCount, String path) {
        this.courseId = courseId;
        this.name = name;
        this.expCount = expCount;
        this.path = path;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpCount() {
        return expCount;
    }

    public void setExpCount(int expCount) {
        this.expCount = expCount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
