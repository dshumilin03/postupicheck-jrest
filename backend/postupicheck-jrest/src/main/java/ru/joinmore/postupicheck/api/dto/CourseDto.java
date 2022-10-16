package ru.joinmore.postupicheck.api.dto;

import java.util.List;

public class CourseDto {

    private long id;
    private String name;
    private String code;

    private long universityId;
    private List<Long> subjectsId;
    private int curPassingPoints;
    private int budgetPlaces;

    public CourseDto(
            long id,
            String name,
            String code,
            long universityId,
            List<Long> subjectsId,
            int curPassingPoints,
            int budgetPlaces) {

        this.id = id;
        this.name = name;
        this.code = code;
        this.universityId = universityId;
        this.subjectsId = subjectsId;
        this.curPassingPoints = curPassingPoints;
        this.budgetPlaces = budgetPlaces;
    }

    public CourseDto() {
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

    public List<Long> getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(List<Long> subjectsId) {
        this.subjectsId = subjectsId;
    }
}
