package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;

@Entity
public class Admission {
    @Id
    @SequenceGenerator(name = "admission_sequence", sequenceName = "admission_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "admission_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    private Boolean consent;
    private Integer points;

    public Admission() {
    }

    public Admission(Student student, Course course, int points) {
        this.student = student;
        this.course = course;
        this.consent = false;
        this.points = points;
    }

    public Admission(Student student, Course course, Boolean consent, int points) {
        this.student = student;
        this.course = course;
        this.consent = consent;
        this.points = points;

    }

    public Admission(Long id, Student student, Course course, int points) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.consent = false;
        this.points = points;

    }


    public Admission(Long id, Student student, Course course, Boolean consent, int points) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.consent = consent;
        this.points = points;

    }

    public Boolean isConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getConsent() {
        return consent;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
