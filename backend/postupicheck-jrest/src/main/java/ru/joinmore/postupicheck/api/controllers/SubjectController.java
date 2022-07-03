package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.facades.SubjectFacade;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectFacade subjectFacade;

    public SubjectController(SubjectFacade subjectFacade) {
        this.subjectFacade = subjectFacade;
    }

    @GetMapping
    List<SubjectDto> getAllSubjects() {
        return subjectFacade.getAll();
    }

    @PostMapping
    SubjectDto createSubject(@RequestBody SubjectDto subject) {
        return subjectFacade.create(subject);
    }

    @GetMapping("/{id}")
    SubjectDto getSubject(@PathVariable Long id) {
        return subjectFacade.get(id);}

    @PutMapping("/{id}")
    SubjectDto replaceSubject(@RequestBody SubjectDto updatedSubject, Long id) {
        return subjectFacade.replace(updatedSubject, id);
    }

    @DeleteMapping("/{id}")
    void deleteSubject(@PathVariable Long id) {
        subjectFacade.delete(id);
    }
}
