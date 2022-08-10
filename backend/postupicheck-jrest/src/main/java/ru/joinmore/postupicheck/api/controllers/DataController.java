package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.services.CourseAdmissionService;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {
    private final CourseAdmissionService courseAdmissionService;

    public DataController(CourseAdmissionService courseAdmissionService) {
        this.courseAdmissionService = courseAdmissionService;
    }

    @PostMapping("/update-passing-scores")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassingScores() {
        courseAdmissionService.updateCourseCurPassingScore();
    }
}
