package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.AdmissionNotFoundException;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.repositories.AdmissionRepository;

import java.util.List;

@Service
public class AdmissionService {

    private final AdmissionRepository repository;

    public AdmissionService(AdmissionRepository repository) {
        this.repository = repository;
    }

    public List<Admission> getAll() {
        return repository.findAll();
    }

    public Admission get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new AdmissionNotFoundException(id));
    }

    public Admission create(Admission admission) {
        return repository.save(admission);
    }

    public Admission replace(Admission updatedAdmission, long id) {
        Admission admission = repository.findById(id) //
                .orElseThrow(() -> new AdmissionNotFoundException(id));
        return replaceAdmission(admission, updatedAdmission);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private Admission replaceAdmission(Admission admission, Admission updatedAdmission) {
        admission.setStudent(updatedAdmission.getStudent());
        admission.setUniversity(updatedAdmission.getUniversity());
        admission.setCourse(updatedAdmission.getCourse());
        return repository.save(admission);
    }
}
