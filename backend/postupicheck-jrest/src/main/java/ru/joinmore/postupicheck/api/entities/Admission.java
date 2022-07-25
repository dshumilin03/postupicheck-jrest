package ru.joinmore.postupicheck.api.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.List;

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

    private Boolean approval;

    public Admission() {
    }

    public Admission(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Admission(Long id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.approval = false;
    }


    public Admission(Long id, Student student, Course course, Boolean approval) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.approval = approval;
    }

    public Boolean isApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
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
