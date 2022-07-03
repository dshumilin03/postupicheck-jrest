package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.facades.AdmissionFacade;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionFacade admissionFacade;

    public AdmissionController(AdmissionFacade admissionFacade) {
        this.admissionFacade = admissionFacade;
    }

    @GetMapping
    List<AdmissionDto> getAllAdmissions() {
        return admissionFacade.getAll();
    }

    @PostMapping
    AdmissionDto createAdmission(@RequestBody AdmissionDto admission) {
        return admissionFacade.create(admission);
    }

    @GetMapping("/{id}")
    AdmissionDto getAdmission(@PathVariable Long id) {
        return admissionFacade.get(id);
    }

    @PutMapping("/{id}")
    AdmissionDto replaceAdmission(@RequestBody AdmissionDto updatedAdmission, Long id) {
        return admissionFacade.replace(updatedAdmission, id);
    }

    @DeleteMapping("/{id}")
    void deleteAdmission(@PathVariable Long id) {
        admissionFacade.delete(id);
    }
}
