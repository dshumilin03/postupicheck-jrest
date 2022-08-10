package ru.joinmore.postupicheck.api.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "student_id, subject_id")
})
public class StudentExamResult {
    @Id
    @SequenceGenerator(name = "studentExamResult_sequence", sequenceName = "studentExamResult_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "studentExamResult_sequence")
    private long id;
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    public StudentExamResult() {
    }

    public StudentExamResult(int points, Student student, Subject subject) {
        this.points = points;
        this.student = student;
        this.subject = subject;
    }

    public StudentExamResult(long id, int points, Student student, Subject subject) {
        this.id = id;
        this.points = points;
        this.student = student;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int result) {
        this.points = result;
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
        return "StudentExamResult{" +
                "id=" + id +
                ", result=" + points +
                ", student=" + student +
                ", subject=" + subject +
                '}';
    }
}
