package ru.joinmore.postupicheck.api.exception;

public class SubjectNotFoundException extends RuntimeException{

    public SubjectNotFoundException(Long id) {
        super("Couldn't found subject with id " + id);
    }
}
