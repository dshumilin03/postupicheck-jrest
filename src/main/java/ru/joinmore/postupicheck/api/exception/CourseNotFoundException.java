package ru.joinmore.postupicheck.api.exception;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(Long id) {
        super("Couldn't found course with id " + id);
    }
}
