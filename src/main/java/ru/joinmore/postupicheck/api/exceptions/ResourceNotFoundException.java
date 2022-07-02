package ru.joinmore.postupicheck.api.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Couldn't found find what was looking for");
    }
}
