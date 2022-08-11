package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @SequenceGenerator(name = "subject_sequence", sequenceName = "subject_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "subject_sequence")
    private long id;
    private String name;
    @OneToMany(mappedBy="firstSubject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> coursesWithFirstSubject;
    @OneToMany(mappedBy="secondSubject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> coursesWithSecondSubject;
    @OneToMany(mappedBy="thirdSubject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> coursesWithThirdSubject;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(long id, String name) {
        this.id = id;
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
                '}';
    }
}
