package ru.joinmore.postupicheck.api.exceptions;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(Long id) {
        super("Couldn't found course with id " + id);
    }
}
