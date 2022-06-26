package ru.joinmore.postupicheck.api.models;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String snils;

    @OneToOne(mappedBy = "student")
    private StudentExamResults studentExamResults;

    @OneToOne(mappedBy = "student")
    private Admission admission;

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}
