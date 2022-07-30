package ru.joinmore.postupicheck.api.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "universities")
public class University {
    @Id
    @SequenceGenerator(name = "university_sequence", sequenceName = "university_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "university_sequence")
    private Long id;
    private String name;

    @OneToMany(mappedBy="university", cascade = CascadeType.ALL)
    private List<Course> courses;

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    public University(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
