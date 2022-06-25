package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;

import ru.joinmore.postupicheck.api.exception.UniversityNotFoundException;
import ru.joinmore.postupicheck.api.model.University;
import ru.joinmore.postupicheck.api.repository.UniversityRepository;

import java.util.List;

@RestController
public class UniversityController {

    private final UniversityRepository repository;

    public UniversityController(UniversityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/universities")
    List<University> all() {
        return repository.findAll();
    }

    @PostMapping("/universities")
    University newUniversity(@RequestBody University university) {
        return repository.save(university);
    }

    @GetMapping("/universities/{id}")
    University one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException(id));}

    @PutMapping("/universities/{id}")
    University replaceUniversity(@RequestBody University updatedUniversity, Long id) {
        return repository.findById(id) //
                .map(university -> {
                    university.setName(updatedUniversity.getName());
                    return repository.save(updatedUniversity);
                })
                .orElseGet(() -> {
                    updatedUniversity.setId(id);
                    return repository.save(updatedUniversity);
                });
    }

    @DeleteMapping("/universities/{id}")
    void deleteUniversity(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

