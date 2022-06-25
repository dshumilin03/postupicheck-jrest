package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.exception.StudentExamResultsNotFoundException;
import ru.joinmore.postupicheck.api.model.StudentExamResults;
import ru.joinmore.postupicheck.api.repository.StudentExamResultsRepository;

import java.util.List;

@RestController
public class StudentExamController {

    private final StudentExamResultsRepository repository;

    public StudentExamController(StudentExamResultsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/examresults")
    List<StudentExamResults> all() {
        return repository.findAll();
    }

    @PostMapping("/examresults")
    StudentExamResults newExamResult(@RequestBody StudentExamResults examResults) {
        return repository.save(examResults);
    }

    @GetMapping("/examresults/{id}")
    StudentExamResults one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentExamResultsNotFoundException(id));
    }

    @PutMapping("/examresults/{id}")
    StudentExamResults replaceExamResults(@RequestBody StudentExamResults updatedExamResults, Long id) {
        return repository.findById(id) //
                .map(examResults -> {
                    examResults.setResult(updatedExamResults.getResult());
                    examResults.setStudent(updatedExamResults.getStudent());
                    examResults.setSubject(updatedExamResults.getSubject());
                    return repository.save(updatedExamResults);
                })
                .orElseGet(() -> {
                    updatedExamResults.setId(id);
                    return repository.save(updatedExamResults);
                });
    }

    @DeleteMapping("/examresults/{id}")
    void deleteExamResult(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
