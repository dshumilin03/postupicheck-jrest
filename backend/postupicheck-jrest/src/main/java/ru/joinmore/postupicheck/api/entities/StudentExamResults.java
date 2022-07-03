package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;

@Entity
@Table
public class StudentExamResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int result;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
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
