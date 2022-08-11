package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code = HttpStatus.OK)
    List<SubjectDto> getAllSubjects() {
        return subjectFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    SubjectDto createSubject(@RequestBody SubjectDto subject) {
        return subjectFacade.create(subject);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    SubjectDto getSubject(@PathVariable Long id) {
        return subjectFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    SubjectDto replaceSubject(@RequestBody SubjectDto updatedSubject, Long id) {
        return subjectFacade.replace(updatedSubject, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteSubject(@PathVariable Long id) {
        subjectFacade.delete(id);
    }
}
