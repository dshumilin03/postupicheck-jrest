package ru.joinmore.postupicheck.api.exception;

public class AdmissionNotFoundException extends RuntimeException{

    public AdmissionNotFoundException(Long id) {
        super("Couldn't found admission with id " + id);
    }
}
