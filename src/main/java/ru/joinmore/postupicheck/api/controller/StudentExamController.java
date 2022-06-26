package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.model.StudentExamResults;
import ru.joinmore.postupicheck.api.service.StudentExamResultsService;

import java.util.List;

@RestController
public class StudentExamController {

    private final StudentExamResultsService service;

    public StudentExamController(StudentExamResultsService service) {
        this.service = service;
    }

    @GetMapping("/examresults")
    List<StudentExamResults> getAllResults() {
        return service.getAllResults();
    }

    @PostMapping("/examresults")
    StudentExamResults createExamResult(@RequestBody StudentExamResults examResults) {
        return service.createStudentExamResults(examResults);
    }

    @GetMapping("/examresults/{id}")
    StudentExamResults one(@PathVariable Long id) {
        return service.getResult(id);
    }

    @PutMapping("/examresults/{id}")
    StudentExamResults replaceExamResults(@RequestBody StudentExamResults updatedExamResults, Long id) {
        return service.replaceExamResults(updatedExamResults, id);
    }

    @DeleteMapping("/examresults/{id}")
    void deleteExamResult(@PathVariable Long id) {
        service.deleteStudent(id);
    }
}
