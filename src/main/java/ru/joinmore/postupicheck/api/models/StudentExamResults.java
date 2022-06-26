package ru.joinmore.postupicheck.api.models;

import javax.persistence.*;

@Entity
@Table
public class StudentExamResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int result;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    public StudentExamResults() {
    }

    public StudentExamResults(int result, Student student, Subject subject) {
        this.result = result;
        this.student = student;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Student getStudent_id() {
        return student;
    }

    public void setStudent_id(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "StudentExamResults{" +
                "id=" + id +
                ", result=" + result +
                ", student=" + student +
                ", subject=" + subject +
                '}';
    }
}
