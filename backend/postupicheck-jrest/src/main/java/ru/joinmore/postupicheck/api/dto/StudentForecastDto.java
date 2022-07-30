package ru.joinmore.postupicheck.api.dto;

public class StudentForecastDto {

    private long id;
    private long admissionId;

    public StudentForecastDto(long id, long admissionId) {
        this.id = id;
        this.admissionId = admissionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(long admissionId) {
        this.admissionId = admissionId;
    }
}
