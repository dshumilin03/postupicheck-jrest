package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.facades.StudentExamResultFacade;

import java.util.List;

@RestController
@RequestMapping("/examresults")
public class StudentExamResultController {

    private final StudentExamResultFacade studentExamResultFacade;

    public StudentExamResultController(StudentExamResultFacade studentExamResultFacade) {
        this.studentExamResultFacade = studentExamResultFacade;
    }

    @GetMapping("/avg")
    Integer getAvgScores() {
        return studentExamResultFacade.getAvgScores();
    }

    @GetMapping
    List<StudentExamResultDto> getAllResults() {
        return studentExamResultFacade.getAll();
    }

    @PostMapping
    StudentExamResultDto createExamResult(@RequestBody StudentExamResultDto studentExamResultDto) {
        return studentExamResultFacade.create(studentExamResultDto);
    }

    @GetMapping("/{id}")
    StudentExamResultDto getExamResult(@PathVariable Long id) {
        return studentExamResultFacade.get(id);
    }

    @PutMapping("/{id}")
    StudentExamResultDto replaceExamResult(@RequestBody StudentExamResultDto updatedStudentExamResult, @PathVariable Long id) {
        return studentExamResultFacade.replace(updatedStudentExamResult, id);
    }

    @DeleteMapping("/{id}")
    void deleteExamResult(@PathVariable Long id) {
        studentExamResultFacade.delete(id);
    }
}
