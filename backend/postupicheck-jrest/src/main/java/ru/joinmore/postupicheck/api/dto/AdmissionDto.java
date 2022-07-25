package ru.joinmore.postupicheck.api.dto;

import org.springframework.beans.factory.annotation.Value;

public class AdmissionDto {

    private long id;
    private long studentId;
    private long courseId;
    private boolean approval;

    public AdmissionDto(long id, long studentId, long courseId, boolean approval) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.approval = approval;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

}
