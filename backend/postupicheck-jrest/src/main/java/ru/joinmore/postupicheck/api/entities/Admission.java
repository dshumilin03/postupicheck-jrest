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

    public Admission() {
    }

    public Admission(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.consent = false;
    }

    public Admission(Student student, Course course, Boolean consent) {
        this.student = student;
        this.course = course;
        this.consent = consent;
    }

    public Admission(Long id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.consent = false;
    }


    public Admission(Long id, Student student, Course course, Boolean consent) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.consent = consent;
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

    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
