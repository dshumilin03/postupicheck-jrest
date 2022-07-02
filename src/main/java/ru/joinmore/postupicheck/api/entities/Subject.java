package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany(mappedBy = "subject")
    private List<StudentExamResults> studentExamResults;

    @OneToMany(mappedBy = "university")
    private List<Course> courseUniversity;

    @OneToMany(mappedBy = "firstSubject")
    private List<Course> courseFirstSubject;

    @OneToMany(mappedBy = "secondSubject")
    private List<Course> courseSecondSubject;

    @OneToMany(mappedBy = "thirdSubject")
    private List<Course> courseThirdSubject;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(List<Course> courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public List<Course> getCourseFirstSubject() {
        return courseFirstSubject;
    }

    public void setCourseFirstSubject(List<Course> courseFirstSubject) {
        this.courseFirstSubject = courseFirstSubject;
    }

    public List<Course> getCourseSecondSubject() {
        return courseSecondSubject;
    }

    public void setCourseSecondSubject(List<Course> courseSecondSubject) {
        this.courseSecondSubject = courseSecondSubject;
    }

    public List<Course> getCourseThirdSubject() {
        return courseThirdSubject;
    }

    public void setCourseThirdSubject(List<Course> courseThirdSubject) {
        this.courseThirdSubject = courseThirdSubject;
    }

    public List<StudentExamResults> getStudentExamResults() {
        return studentExamResults;
    }

    public void setStudentExamResults(List<StudentExamResults> studentExamResults) {
        this.studentExamResults = studentExamResults;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentExamResults=" + studentExamResults +
                '}';
    }
}
