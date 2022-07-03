package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String snils;

    @OneToMany(mappedBy = "student")
    private List<StudentExamResults> studentExamResults;

    @OneToMany(mappedBy = "student")
    private List<Admission> admission;

    public Student() {}

    public Student(String name, String snils) {
        this.name = name;
        this.snils = snils;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudentExamResults> getStudentExamResults() {
        return studentExamResults;
    }

    public void setStudentExamResults(List<StudentExamResults> studentExamResults) {
        this.studentExamResults = studentExamResults;
    }

    public List<Admission> getAdmission() {
        return admission;
    }

    public void setAdmission(List<Admission> admission) {
        this.admission = admission;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}