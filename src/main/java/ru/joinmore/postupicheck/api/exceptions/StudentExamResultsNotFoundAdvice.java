package ru.joinmore.postupicheck.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudentExamResultsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(StudentExamResultsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String examResultsNotFoundHandler(StudentExamResultsNotFoundException ex) {
        return ex.getMessage();
    }
}