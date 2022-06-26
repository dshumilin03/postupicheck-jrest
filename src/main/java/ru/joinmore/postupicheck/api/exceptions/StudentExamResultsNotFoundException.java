package ru.joinmore.postupicheck.api.exceptions;

public class StudentExamResultsNotFoundException extends RuntimeException{

    public StudentExamResultsNotFoundException(Long id) {
        super("Couldn't found exam result with id " + id);
    }
}
