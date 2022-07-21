package ru.joinmore.postupicheck.api.exceptions;

public class AlreadyExistsException extends RuntimeException{
        public AlreadyExistsException(String resource) {
                super(resource + " is exists");
        }
}
