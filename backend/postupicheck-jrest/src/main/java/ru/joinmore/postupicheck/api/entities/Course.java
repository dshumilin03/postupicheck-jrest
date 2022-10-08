package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = @Index(columnList = "id"))
public class Course {

    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    private Long id;
    private String name;
    private String code;
    private Integer curPassingPoints;
    private Integer budgetPlaces;
    private Integer availableBudgetPlaces;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;
    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private List<Admission> admissions;
    @OneToMany(mappedBy= "course", cascade = CascadeType.ALL)
    private List<CourseRequiredSubject> requiredSubjects;

    public Course() {
    }

    public Course(
            String name,
            String code,
            University university,
            Integer curPassingPoints,
            Integer budgetPlaces) {
        this.name = name;
        this.code = code;
        this.university = university;
        this.curPassingPoints = curPassingPoints;
        this.budgetPlaces = budgetPlaces;
    }

    public Course(Long id,
                  String name,
                  String code,
                  University university,
                  Integer curPassingPoints,
                  Integer budgetPlaces) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.university = university;
        this.curPassingPoints = curPassingPoints;
        this.budgetPlaces = budgetPlaces;
        this.availableBudgetPlaces = budgetPlaces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Subject> getRequiredSubjects() {
        List<Subject> subjects = new ArrayList<>();
        requiredSubjects.forEach(requiredSubject -> subjects.add(requiredSubject.getSubject()));
        return subjects;
    }

    public void setRequiredSubjects(List<CourseRequiredSubject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }

    public Integer getCurPassingPoints() {
        return curPassingPoints;
    }

    public void setCurPassingPoints(Integer curPassingPoints) {
        this.curPassingPoints = curPassingPoints;
    }

    public Integer getBudgetPlaces() {
        return budgetPlaces;
    }

    public void setBudgetPlaces(Integer budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

//    public Subject getFirstSubject() {
//        return firstSubject;
//    }
//
//    public void setFirstSubject(Subject firstSubject) {
//        this.firstSubject = firstSubject;
//    }
//
//    public Subject getSecondSubject() {
//        return secondSubject;
//    }
//
//    public void setSecondSubject(Subject secondSubject) {
//        this.secondSubject = secondSubject;
//    }
//
//    public Subject getThirdSubject() {
//        return thirdSubject;
//    }
//
//    public void setThirdSubject(Subject thirdSubject) {
//        this.thirdSubject = thirdSubject;
//    }

    public Integer getAvailableBudgetPlaces() {
        return availableBudgetPlaces;
    }

    public void setAvailableBudgetPlaces(Integer availableBudgetPlaces) {
        this.availableBudgetPlaces = availableBudgetPlaces;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", curPassingPoints=" + curPassingPoints +
                ", budgetPlaces=" + budgetPlaces +
                ", availableBudgetPlaces=" + availableBudgetPlaces +
                ", university=" + university +
                ", courses=" + admissions +
                '}';
    }
}
