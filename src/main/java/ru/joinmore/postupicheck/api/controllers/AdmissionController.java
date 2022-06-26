package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.models.Admission;
import ru.joinmore.postupicheck.api.services.AdmissionService;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService service) {
        this.admissionService = service;
    }

    @GetMapping
    List<Admission> getAllAdmissions() {
        return admissionService.getAll();
    }

    @PostMapping
    Admission createAdmission(@RequestBody Admission admission) {
        return admissionService.create(admission);
    }

    @GetMapping("/{id}")
    Admission getAdmission(@PathVariable Long id) {
        return admissionService.get(id);
    }

    @PutMapping("/{id}")
    Admission replaceAdmission(@RequestBody Admission updatedAdmission, Long id) {
        return admissionService.replace(updatedAdmission, id);
    }

    @DeleteMapping("/{id}")
    void deleteAdmission(@PathVariable Long id) {
        admissionService.delete(id);
    }
}
