package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;

import ru.joinmore.postupicheck.api.model.University;
import ru.joinmore.postupicheck.api.service.UniversityService;

import java.util.List;

@RestController
public class UniversityController {

    private final UniversityService service;

    public UniversityController(UniversityService service) {
        this.service = service;
    }

    @GetMapping("/universities")
    List<University> getAllUniversities() {
        return service.getAllUniversities();
    }

    @PostMapping("/universities")
    University createUniversity(@RequestBody University university) {
        return service.createUniversity(university);
    }

    @GetMapping("/universities/{id}")
    University getUniversity(@PathVariable Long id) {
        return service.getUniversity(id);}

    @PutMapping("/universities/{id}")
    University replaceUniversity(@RequestBody University updatedUniversity, Long id) {
        return service.replaceUniversity(updatedUniversity, id);
    }

    @DeleteMapping("/universities/{id}")
    void deleteUniversity(@PathVariable Long id) {
        service.deleteUniversity(id);
    }
}

