package ru.joinmore.postupicheck.api.exceptions;

public class ResourceNotExistsException extends RuntimeException {

    public ResourceNotExistsException(String resource) {
        super(resource + " is not exists");
    }

}
