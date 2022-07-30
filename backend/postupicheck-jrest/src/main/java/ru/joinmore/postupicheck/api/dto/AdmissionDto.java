package ru.joinmore.postupicheck.api.dto;

public class AdmissionDto {

    private long id;
    private long studentId;
    private long courseId;
    private boolean consent;

    public AdmissionDto(long id, long studentId, long courseId, boolean consent) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.consent = consent;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
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
