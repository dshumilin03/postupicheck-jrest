package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.exception.AdmissionNotFoundException;
import ru.joinmore.postupicheck.api.model.Admission;
import ru.joinmore.postupicheck.api.repository.AdmissionRepository;

import java.util.List;

@RestController
public class AdmissionController {

    private final AdmissionRepository repository;

    public AdmissionController(AdmissionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admissions")
    List<Admission> all() {
        return repository.findAll();
    }

    @PostMapping("/admissions")
    Admission newAdmission(@RequestBody Admission admission) {
        return repository.save(admission);
    }

    @GetMapping("/admissions/{id}")
    Admission one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AdmissionNotFoundException(id));}

    @PutMapping("/admissions/{id}")
    Admission replaceAdmission(@RequestBody Admission updatedAdmission, Long id) {
        return repository.findById(id) //
                .map(admission -> {
                    admission.setStudent(updatedAdmission.getStudent());
                    admission.setUniversity(updatedAdmission.getUniversity());
                    admission.setCourse(updatedAdmission.getCourse());
                    return repository.save(updatedAdmission);
                })
                .orElseGet(() -> {
                    updatedAdmission.setId(id);
                    return repository.save(updatedAdmission);
                });
    }

    @DeleteMapping("/admissions/{id}")
    void deleteAdmission(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
