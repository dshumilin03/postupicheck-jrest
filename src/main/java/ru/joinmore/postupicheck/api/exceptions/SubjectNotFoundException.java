package ru.joinmore.postupicheck.api.exceptions;

public class SubjectNotFoundException extends RuntimeException{

    public SubjectNotFoundException(Long id) {
        super("Couldn't found subject with id " + id);
    }
}
