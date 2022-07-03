package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;

import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.facades.UniversityFacade;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityFacade universityFacade;

    public UniversityController(UniversityFacade universityFacade) {
        this.universityFacade = universityFacade;
    }

    @GetMapping
    List<UniversityDto> getAllUniversities() {
        return universityFacade.getAll();
    }

    @PostMapping
    UniversityDto createUniversity(@RequestBody UniversityDto university) {
        return universityFacade.create(university);
    }

    @GetMapping("/{id}")
    UniversityDto getUniversity(@PathVariable Long id) {
        return universityFacade.get(id);}

    @PutMapping("/{id}")
    UniversityDto replaceUniversity(@RequestBody UniversityDto updatedUniversity, Long id) {
        return universityFacade.replace(updatedUniversity, id);
    }

    @DeleteMapping("/{id}")
    void deleteUniversity(@PathVariable Long id) {
        universityFacade.delete(id);
    }
}

