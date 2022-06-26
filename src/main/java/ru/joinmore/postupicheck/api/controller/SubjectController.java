package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.model.Subject;
import ru.joinmore.postupicheck.api.service.SubjectService;

import java.util.List;

@RestController
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping("/subjects")
    List<Subject> getAllSubjects() {
        return service.getAllSubjects();
    }

    @PostMapping("/subjects")
    Subject createSubject(@RequestBody Subject subject) {
        return service.createSubject(subject);
    }

    @GetMapping("/subjects/{id}")
    Subject getSubject(@PathVariable Long id) {
        return service.getSubject(id);}

    @PutMapping("/subjects/{id}")
    Subject replaceSubject(@RequestBody Subject updatedSubject, Long id) {
        return service.replaceSubject(updatedSubject, id);
    }

    @DeleteMapping("/subjects/{id}")
    void deleteSubject(@PathVariable Long id) {
        service.deleteSubject(id);
    }
}
