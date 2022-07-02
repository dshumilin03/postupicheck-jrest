package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;

    @ManyToOne
    @JoinColumn(name = "first_subject_id", referencedColumnName = "id")
    private Subject firstSubject;

    @ManyToOne
    @JoinColumn(name = "second_subject_id", referencedColumnName = "id")
    private Subject secondSubject;

    @ManyToOne
    @JoinColumn(name = "third_subject_id", referencedColumnName = "id")
    private Subject thirdSubject;

    @OneToMany(mappedBy = "course")
    private List<Admission> admission;

    public Course() {
    }

    public Course(String name, String code, University university, Subject firstSubject, Subject secondSubject, Subject thirdSubject) {
        this.name = name;
        this.code = code;
        this.university = university;
        this.firstSubject = firstSubject;
        this.secondSubject = secondSubject;
        this.thirdSubject = thirdSubject;
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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Subject getFirstSubject() {
        return firstSubject;
    }

    public void setFirstSubject(Subject firstSubject) {
        this.firstSubject = firstSubject;
    }

    public Subject getSecondSubject() {
        return secondSubject;
    }

    public void setSecondSubject(Subject secondSubject) {
        this.secondSubject = secondSubject;
    }

    public Subject getThirdSubject() {
        return thirdSubject;
    }

    public void setThirdSubject(Subject thirdSubject) {
        this.thirdSubject = thirdSubject;
    }

    public List<Admission> getAdmission() {
        return admission;
    }

    public void setAdmission(List<Admission> admission) {
        this.admission = admission;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", university=" + university +
                ", firstSubject=" + firstSubject +
                ", secondSubject=" + secondSubject +
                ", thirdSubject=" + thirdSubject +
                '}';
    }
}
