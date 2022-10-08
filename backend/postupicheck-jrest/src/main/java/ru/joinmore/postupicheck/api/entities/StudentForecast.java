package ru.joinmore.postupicheck.api.entities;

import javax.persistence.*;

@Entity
@Table(name = "student_forecasts")
public class StudentForecast {

    @Id
    @SequenceGenerator(name = "studentForecast_sequence", sequenceName = "studentForecast_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "studentForecast_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admission_id", referencedColumnName = "id")
    private Admission admission;

    public StudentForecast() {
    }

    public StudentForecast(Long id, Admission admission) {
        this.id = id;
        this.admission = admission;
    }

    public StudentForecast(Admission admission) {
        this.admission = admission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }
}
