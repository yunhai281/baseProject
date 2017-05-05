package com.boyuyun.base.course.entity;

import java.io.Serializable;

import com.dhcc.common.db.page.Page;

public class TeacherCourse  extends Page implements Serializable{
    private String id;

    private String teacherId;

    private String courseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }
}