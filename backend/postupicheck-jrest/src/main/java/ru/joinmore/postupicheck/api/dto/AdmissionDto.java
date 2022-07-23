package ru.joinmore.postupicheck.api.dto;

public class AdmissionDto {

    private long id;
    private long studentId;
    private long courseId;
    private long universityId;

    public AdmissionDto(long id, long studentId, long courseId, long universityId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.universityId = universityId;
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

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }
}
