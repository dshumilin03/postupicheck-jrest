package ru.joinmore.postupicheck.api.dto;

public class CourseDto {

    private long id;
    private String name;
    private String code;

    private long universityId;
    private long firstSubjectId;
    private long secondSubjectId;
    private long thirdSubjectId;
    private int curPassingPoints;
    private int budgetPlaces;

    public CourseDto(
            long id,
            String name,
            String code,
            long universityId,
            long firstSubjectId,
            long secondSubjectId,
            long thirdSubjectId,
            int curPassingPoints,
            int budgetPlaces) {

        this.id = id;
        this.name = name;
        this.code = code;
        this.universityId = universityId;
        this.firstSubjectId = firstSubjectId;
        this.secondSubjectId = secondSubjectId;
        this.thirdSubjectId = thirdSubjectId;
        this.curPassingPoints = curPassingPoints;
        this.budgetPlaces = budgetPlaces;
    }

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public void setBudgetPlaces(int budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public int getCurPassingPoints() {
        return curPassingPoints;
    }

    public void setCurPassingPoints(int curPassingPoints) {
        this.curPassingPoints = curPassingPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getFirstSubjectId() {
        return firstSubjectId;
    }

    public void setFirstSubjectId(long firstSubjectId) {
        this.firstSubjectId = firstSubjectId;
    }

    public long getSecondSubjectId() {
        return secondSubjectId;
    }

    public void setSecondSubjectId(long secondSubjectId) {
        this.secondSubjectId = secondSubjectId;
    }

    public long getThirdSubjectId() {
        return thirdSubjectId;
    }

    public void setThirdSubjectId(long thirdSubjectId) {
        this.thirdSubjectId = thirdSubjectId;
    }
}
