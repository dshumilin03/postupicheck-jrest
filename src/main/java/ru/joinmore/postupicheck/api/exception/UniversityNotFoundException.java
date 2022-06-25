package ru.joinmore.postupicheck.api.exception;

public class UniversityNotFoundException extends RuntimeException{

    public UniversityNotFoundException(Long id) {
        super("Couldn't found university with id " + id);
    }
}
