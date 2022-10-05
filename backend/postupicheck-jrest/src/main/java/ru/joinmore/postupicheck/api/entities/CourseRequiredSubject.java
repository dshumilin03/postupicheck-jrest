package ru.joinmore.postupicheck.api.entities;


import javax.persistence.*;

@Entity
// нужны ли индексы?
@Table(indexes = {
        @Index(columnList = "course_id"),
        @Index(columnList = "required_subject_id"),
})
public class CourseRequiredSubject {

    @Id
    @SequenceGenerator(name = "course_required_subject_sequence",
            sequenceName = "course_required_subject_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_required_subject_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "required_subject_id", referencedColumnName = "id")
    private Subject subject;

    public CourseRequiredSubject() {}

    public CourseRequiredSubject(Course courses, Subject subject) {
        this.course = courses;
        this.subject = subject;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Subject getSubject() {
        return subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
