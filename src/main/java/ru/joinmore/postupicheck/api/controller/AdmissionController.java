package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.model.Admission;
import ru.joinmore.postupicheck.api.service.AdmissionService;

import java.util.List;

@RestController
public class AdmissionController {

    private final AdmissionService service;

    public AdmissionController(AdmissionService service) {
        this.service = service;
    }

    @GetMapping("/admissions")
    List<Admission> getAllAdmissions() {
        return service.getAllAdmissions();
    }

    @PostMapping("/admissions")
    Admission createAdmission(@RequestBody Admission admission) {
        return service.createAdmission(admission);
    }

    @GetMapping("/admissions/{id}")
    Admission getAdmission(@PathVariable Long id) {
        return service.getAdmission(id);
    }

    @PutMapping("/admissions/{id}")
    Admission replaceAdmission(@RequestBody Admission updatedAdmission, Long id) {
        return service.replaceAdmission(updatedAdmission, id);
    }

    @DeleteMapping("/admissions/{id}")
    void deleteAdmission(@PathVariable Long id) {
        service.deleteAdmission(id);
    }
}
