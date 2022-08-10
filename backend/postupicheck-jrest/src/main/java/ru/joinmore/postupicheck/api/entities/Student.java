package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;
    private String name;
    private String snils;

    @OneToMany(mappedBy="student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Admission> admissions;

    @OneToMany(mappedBy="student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentExamResult> studentExamResults;

    public Student() {}

    public Student(String name, String snils) {
        this.name = name;
        this.snils = snils;
    }

    public Student(Long id, String name, String snils) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}
