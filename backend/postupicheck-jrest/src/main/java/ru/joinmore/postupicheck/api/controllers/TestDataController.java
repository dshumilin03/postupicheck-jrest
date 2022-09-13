package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.services.TestDataService;

@RestController
@RequestMapping("/test-data")
public class TestDataController {

    private final TestDataService testDataService;

    public TestDataController(TestDataService testDataService) {
        this.testDataService = testDataService;
    }

    @PostMapping("/create-universities")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestUniversities() {
        testDataService.createTestUniversities();
    }

    @PostMapping("/create-examresults")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestStudentExamResults() {
        testDataService.createTestStudentExamResults();
    }

    @PostMapping("/create-students")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestStudents() {
        testDataService.createTestStudents();
    }

    @PostMapping("/create-courses")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestCourses() {
        testDataService.createTestCourses();
    }

    @PostMapping("/create-admissions")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestAdmissions() {
        testDataService.createTestAdmissions();
    }

    @PostMapping("/create-subjects")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createTestSubjects() {
        testDataService.createSubjects();
    }

    @PostMapping("/set-random-budget-places")
    @ResponseStatus(code = HttpStatus.OK)
    void setRandomBudgetPlacesForAllCourses() {
        testDataService.setRandomBudgetPlacesForAllCourses();
    }

    @PostMapping("/set-points-for-all-admissions")
    @ResponseStatus(code = HttpStatus.OK)
    void setPointsForAllAdmissions() {
        testDataService.setPointsForAllAdmissions();
    }

    @PostMapping("/set-preferentials")
    @ResponseStatus(code = HttpStatus.OK)
    void setPreferentials() {
        testDataService.setPreferential();
    }
}
