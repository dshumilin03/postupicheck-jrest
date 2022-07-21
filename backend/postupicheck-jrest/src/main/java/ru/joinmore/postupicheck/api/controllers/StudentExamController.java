package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.facades.StudentExamResultsFacade;

import java.util.List;

@RestController
@RequestMapping("/examresults")
public class StudentExamController {

    private final StudentExamResultsFacade studentExamResultsFacade;

    public StudentExamController(StudentExamResultsFacade studentExamResultsFacade) {
        this.studentExamResultsFacade = studentExamResultsFacade;
    }

    @GetMapping("/avg")
    Integer getAvgScores() {
        return studentExamResultsFacade.getAvgScores();
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
    StudentExamResultsDto getExamResult(@PathVariable Long id) {
        return studentExamResultsFacade.get(id);
    }

    @PutMapping("/{id}")
    StudentExamResultsDto replaceExamResults(@RequestBody StudentExamResultsDto updatedExamResults, @PathVariable Long id) {
        return studentExamResultsFacade.replace(updatedExamResults, id);
    }

    @DeleteMapping("/{id}")
    void deleteExamResult(@PathVariable Long id) {
        studentExamResultsFacade.delete(id);
    }
}
