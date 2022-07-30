package ru.joinmore.postupicheck.api.exceptions;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ResourceNotExistsException extends RuntimeException {

    public ResourceNotExistsException(String resource) {
        super(resource + " is not exists");
    }

}
