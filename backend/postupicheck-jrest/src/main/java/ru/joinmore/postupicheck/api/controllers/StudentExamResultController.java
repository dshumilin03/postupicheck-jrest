package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.facades.StudentExamResultFacade;

import java.util.List;

@RestController
@RequestMapping("/exam-results")
public class StudentExamResultController {

    private final StudentExamResultFacade studentExamResultFacade;

    public StudentExamResultController(StudentExamResultFacade studentExamResultFacade) {
        this.studentExamResultFacade = studentExamResultFacade;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    List<StudentExamResultDto> getAllResults() {
        return studentExamResultFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    StudentExamResultDto createExamResult(@RequestBody StudentExamResultDto studentExamResultDto) {
        return studentExamResultFacade.create(studentExamResultDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentExamResultDto getExamResult(@PathVariable Long id) {
        return studentExamResultFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    StudentExamResultDto replaceExamResult(@RequestBody StudentExamResultDto updatedStudentExamResult,
                                           @PathVariable Long id) {
        return studentExamResultFacade.replace(updatedStudentExamResult, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteExamResult(@PathVariable Long id) {
        studentExamResultFacade.delete(id);
    }

    @GetMapping("/student")
    @ResponseStatus(code = HttpStatus.OK)
    List<StudentExamResultDto> getAllStudentResults(@RequestParam Long id) {
        return studentExamResultFacade.getAllStudentResults(id);
    }
}
