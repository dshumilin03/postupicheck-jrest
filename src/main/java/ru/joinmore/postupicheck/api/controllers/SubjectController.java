package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.models.Subject;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService service) {
        this.subjectService = service;
    }

    @GetMapping
    List<Subject> getAllSubjects() {
        return subjectService.getAll();
    }

    @PostMapping
    Subject createSubject(@RequestBody Subject subject) {
        return subjectService.create(subject);
    }

    @GetMapping("/{id}")
    Subject getSubject(@PathVariable Long id) {
        return subjectService.get(id);}

    @PutMapping("/{id}")
    Subject replaceSubject(@RequestBody Subject updatedSubject, Long id) {
        return subjectService.replace(updatedSubject, id);
    }

    @DeleteMapping("/{id}")
    void deleteSubject(@PathVariable Long id) {
        subjectService.delete(id);
    }
}
