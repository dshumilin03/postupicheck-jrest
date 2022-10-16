package ru.joinmore.postupicheck.api.dto;

public class StudentDto {

    private long id;
    private String name;
    private String snils;
    private boolean preferential;

    public StudentDto(long id, String name, String snils) {
        this.id = id;
        this.name = name;
        this.snils = snils;
        this.preferential = false;
    }

    public StudentDto() {
    }

    public StudentDto(long id, String name, String snils, boolean preferential) {
        this.id = id;
        this.name = name;
        this.snils = snils;
        this.preferential = preferential;
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

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public boolean isPreferential() {
        return preferential;
    }

    public void setPreferential(boolean preferential) {
        this.preferential = preferential;
    }
}
