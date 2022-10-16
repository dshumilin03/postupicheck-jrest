package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.facades.AdmissionFacade;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class  AdmissionController {

    private final AdmissionFacade admissionFacade;

    public AdmissionController(AdmissionFacade admissionFacade) {
        this.admissionFacade = admissionFacade;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    List<AdmissionDto> getAllAdmissions() {
        return admissionFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    AdmissionDto createAdmission(@RequestBody AdmissionDto admission) {
        return admissionFacade.create(admission);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    AdmissionDto getAdmission(@PathVariable Long id) {
        return admissionFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    AdmissionDto replaceAdmission(@RequestBody AdmissionDto updatedAdmission,
                                  @PathVariable Long id) {
        return admissionFacade.replace(updatedAdmission, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteAdmission(@PathVariable Long id) {
        admissionFacade.delete(id);
    }

    @GetMapping("/course/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    List<AdmissionDto> getCourseAdmissions(@PathVariable Long id) {
        return admissionFacade.getCourseAdmissions(id);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteAll() {
        admissionFacade.deleteAll();
    }

}
