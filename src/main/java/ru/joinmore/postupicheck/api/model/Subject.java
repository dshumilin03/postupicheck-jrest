package ru.joinmore.postupicheck.api.model;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToOne(mappedBy = "subject")
    private StudentExamResults studentExamResults;

    @OneToOne(mappedBy = "university")
    private Course courseUniversity;

    @OneToOne(mappedBy = "firstSubject")
    private Course courseFirstSubject;

    @OneToOne(mappedBy = "secondSubject")
    private Course courseSecondSubject;

    @OneToOne(mappedBy = "thirdSubject")
    private Course courseThirdSubject;

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

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentExamResults=" + studentExamResults +
                '}';
    }
}
