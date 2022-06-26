package ru.joinmore.postupicheck.api.exceptions;

public class AdmissionNotFoundException extends RuntimeException{

    public AdmissionNotFoundException(Long id) {
        super("Couldn't found admission with id " + id);
    }
}
