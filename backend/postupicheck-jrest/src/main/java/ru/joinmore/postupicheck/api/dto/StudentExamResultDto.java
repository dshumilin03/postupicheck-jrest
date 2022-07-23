package ru.joinmore.postupicheck.api.dto;

public class StudentExamResultDto {

    private long id;
    private long studentId;
    private long subjectId;
    private int points;

    public StudentExamResultDto(long id, long studentId, long subjectId, int points) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.points = points;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
