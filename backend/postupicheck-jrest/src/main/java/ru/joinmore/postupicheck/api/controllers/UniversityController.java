package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.facades.UniversityFacade;

import java.util.List;

@RestController
@RequestMapping("/universities")
public class UniversityController {

    private final UniversityFacade universityFacade;

    public UniversityController(UniversityFacade universityFacade) {
        this.universityFacade = universityFacade;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    List<UniversityDto> getAllUniversities() {
        return universityFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    UniversityDto createUniversity(@RequestBody UniversityDto university) {
        return universityFacade.create(university);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    UniversityDto getUniversity(@PathVariable Long id) {
        return universityFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    UniversityDto replaceUniversity(@RequestBody UniversityDto updatedUniversity,
                                    @PathVariable Long id) {
        return universityFacade.replace(updatedUniversity, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteUniversity(@PathVariable Long id) {
        universityFacade.delete(id);
    }

}

