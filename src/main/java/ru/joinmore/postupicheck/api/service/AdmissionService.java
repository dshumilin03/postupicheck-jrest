package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.AdmissionNotFoundException;
import ru.joinmore.postupicheck.api.model.Admission;
import ru.joinmore.postupicheck.api.repository.AdmissionRepository;

import java.util.List;

@Service
public class AdmissionService {

    private final AdmissionRepository repository;

    public AdmissionService(AdmissionRepository repository) {
        this.repository = repository;
    }

    public List<Admission> getAllAdmissions() {
        return repository.findAll();
    }

    public Admission getAdmission(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new AdmissionNotFoundException(id));
    }

    public Admission createAdmission(Admission admission) {
        return repository.save(admission);
    }

    public Admission replaceAdmission(Admission updatedAdmission, Long id) {
        Admission admission = repository.findById(id) //
                .orElseThrow(() -> new AdmissionNotFoundException(id));
        return replaceAdmission(admission, updatedAdmission);
    }

    public void deleteAdmission(Long id) {
        repository.deleteById(id);
    }

    private Admission replaceAdmission(Admission admission, Admission updatedAdmission) {
        admission.setStudent(updatedAdmission.getStudent());
        admission.setUniversity(updatedAdmission.getUniversity());
        admission.setCourse(updatedAdmission.getCourse());
        return repository.save(updatedAdmission);
    }
}
