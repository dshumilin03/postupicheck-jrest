package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;

import ru.joinmore.postupicheck.api.models.University;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService service) {
        this.universityService = service;
    }

    @GetMapping
    List<University> getAllUniversities() {
        return universityService.getAll();
    }

    @PostMapping
    University createUniversity(@RequestBody University university) {
        return universityService.create(university);
    }

    @GetMapping("/{id}")
    University getUniversity(@PathVariable Long id) {
        return universityService.get(id);}

    @PutMapping("/{id}")
    University replaceUniversity(@RequestBody University updatedUniversity, Long id) {
        return universityService.replace(updatedUniversity, id);
    }

    @DeleteMapping("/{id}")
    void deleteUniversity(@PathVariable Long id) {
        universityService.delete(id);
    }
}

