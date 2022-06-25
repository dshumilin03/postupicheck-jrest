package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.exception.StudentNotFoundException;
import ru.joinmore.postupicheck.api.exception.SubjectNotFoundException;
import ru.joinmore.postupicheck.api.model.Student;
import ru.joinmore.postupicheck.api.model.Subject;
import ru.joinmore.postupicheck.api.repository.SubjectRepository;

import java.util.List;

@RestController
public class SubjectController {

    private final SubjectRepository repository;

    public SubjectController(SubjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/subjects")
    List<Subject> all() {
        return repository.findAll();
    }

    @PostMapping("/subjects")
    Subject newSubject(@RequestBody Subject subject) {
        return repository.save(subject);
    }

    @GetMapping("/subjects/{id}")
    Subject one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));}

    @PutMapping("/subjects/{id}")
    Subject replaceSubject(@RequestBody Subject updatedSubject, Long id) {
        return repository.findById(id) //
                .map(subject -> {
                    subject.setName(updatedSubject.getName());
                    return repository.save(updatedSubject);
                })
                .orElseGet(() -> {
                    updatedSubject.setId(id);
                    return repository.save(updatedSubject);
                });
    }

    @DeleteMapping("/subjects/{id}")
    void deleteSubject(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
