package ru.joinmore.postupicheck.api.dto;

public class UniversityDto {

    private long id;
    private String name;

    public UniversityDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UniversityDto() {
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
}
