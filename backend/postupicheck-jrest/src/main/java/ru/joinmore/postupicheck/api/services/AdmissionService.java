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
        return repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Admission with id [" + id + "]"));
    }

    public Admission create(Admission admission) {
        String courseName = admission
                .getCourse()
                .getName();
        Student student = admission.getStudent();
        Boolean exists = repository.existsByCourseNameAndStudent(courseName, student);

        if (exists) {
            String message = String.format(
                    "Admission with student %s and courseName %s",
                    student.getName(),
                    courseName);
            throw new AlreadyExistsException(message);
        }

        return repository.save(admission);
    }

    public Admission replace(Admission updatedAdmission, long id) {
        Admission admission = repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Admission with id [" + id + "]"));

        return replaceAdmission(admission, updatedAdmission);
    }

    // TODO fix to findAdmissionsByStudentId
    @Deprecated
    public List<Admission> findAdmissionsByStudent(Student student) {
        return repository.findAdmissionsByStudent(student);
    }

    public Admission getStudentAdmission(Course course, Student student) {
        return repository.findAdmissionByStudentAndCourse(student, course);
    }

    public List<Admission> findAdmissionsByStudentAndUniversity(Student student, University university) {
        return repository.findAdmissionsByStudentAndUniversity(student, university);
    }

    public List<Admission> getCourseAdmissions(Course course) {
        return repository.findAdmissionsByCourseOrderByPoints(course);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Admission with id [" + id + "]");
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private Admission replaceAdmission(Admission admission, Admission updatedAdmission) {
        admission.setStudent(updatedAdmission.getStudent());
        admission.setCourse(updatedAdmission.getCourse());
        admission.setConsent(updatedAdmission.isConsent());
        admission.setPoints(updatedAdmission.getPoints());

        return repository.save(admission);
    }

    public List<Admission> findAdmissionsByStudentId(Long id) {
        List<Admission> studentAdmissions = repository.findAdmissionsByStudentId(id);

        if (studentAdmissions.isEmpty()) {
            throw new ResourceNotExistsException(String.format("Admissions by student %d", id));
        }

        return studentAdmissions;
    }

    public List<Admission> saveAll(List<Admission> admissions) {
        return repository.saveAll(admissions);
    }
}
