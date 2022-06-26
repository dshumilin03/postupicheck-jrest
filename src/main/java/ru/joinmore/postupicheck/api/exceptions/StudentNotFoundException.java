package ru.joinmore.postupicheck.api.exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Couldn't found student with id " + id);
    }

}
