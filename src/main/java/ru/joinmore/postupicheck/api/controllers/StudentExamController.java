package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.facades.StudentExamResultsFacade;
import ru.joinmore.postupicheck.api.models.Student;
import ru.joinmore.postupicheck.api.models.StudentExamResults;
import ru.joinmore.postupicheck.api.models.Subject;
import ru.joinmore.postupicheck.api.services.StudentExamResultsService;

import java.util.List;

@RestController
@RequestMapping("/examresults")
public class StudentExamController {

    private final StudentExamResultsService studentExamResultsService;
    private final StudentExamResultsFacade studentExamResultsFacade;

    public StudentExamController(StudentExamResultsService service, StudentExamResultsFacade studentExamResultsFacade) {
        this.studentExamResultsService = service;
        this.studentExamResultsFacade = studentExamResultsFacade;
    }

    @GetMapping
    List<StudentExamResultsDto> getAllResults() {
        return studentExamResultsFacade.getAll();
    }

    @PostMapping
    StudentExamResultsDto createExamResult(@RequestBody StudentExamResultsDto examResults) {
        return studentExamResultsFacade.create(examResults);
    }

    @GetMapping("/{id}")
    StudentExamResults getExamResult(@PathVariable Long id) {
        return studentExamResultsService.get(id);
    }

    @PutMapping("/{id}")
    StudentExamResults replaceExamResults(@RequestBody StudentExamResults updatedExamResults, Long id) {
        return studentExamResultsService.replace(updatedExamResults, id);
    }

    @DeleteMapping("/{id}")
    void deleteExamResult(@PathVariable Long id) {
        studentExamResultsService.delete(id);
    }
}
