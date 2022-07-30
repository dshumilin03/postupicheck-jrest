package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
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
                .orElseThrow(() -> new ResourceNotExistsException("Admission with id" + id));
    }

    public Admission create(Admission admission) {
        Student student = admission.getStudent();
        String courseName = admission.getCourse().getName();
        Boolean exists = repository.existsByCourse_NameAndStudent(courseName, student);

        if (exists) {
            String message = String.format("Admission with student %s and courseName %s",
                    student.getName(),
                    courseName);
            throw new AlreadyExistsException(message);
        }
        return repository.save(admission);
    }

    public Admission replace(Admission updatedAdmission, long id) {
        Admission admission = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Admission with id" + id));
        return replaceAdmission(admission, updatedAdmission);
    }

    public List<Admission> findAdmissionsByStudent(Student student) {
        return repository.findAdmissionsByStudent(student);
    }

    public List<Admission> findAdmissionsByStudentAndCourse_University(Student student, University university) {
        return repository.findAdmissionsByStudentAndCourse_University(student, university);
    }

    public void delete(long id) {

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Course with id " + id);
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private Admission replaceAdmission(Admission admission, Admission updatedAdmission) {
        admission.setStudent(updatedAdmission.getStudent());
        admission.setCourse(updatedAdmission.getCourse());
        admission.setConsent(updatedAdmission.isConsent());
        return repository.save(admission);
    }
}
