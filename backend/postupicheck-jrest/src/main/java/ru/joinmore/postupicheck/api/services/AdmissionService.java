package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
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
        Course course = admission.getCourse();
        Boolean exists = repository.existsByCourseAndStudent(course, student);

        if (exists) {
            String message = String.format("Admission with student %s and code %s",
                    student.getName(),
                    course.getCode());
            throw new AlreadyExistsException(message);
        }
        return repository.save(admission);
    }

    public Admission replace(Admission updatedAdmission, long id) {
        Admission admission = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Admission with id" + id));
        return replaceAdmission(admission, updatedAdmission);
    }

    public void delete(long id) {

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Course with id " + id);
        }
    }

    private Admission replaceAdmission(Admission admission, Admission updatedAdmission) {
        admission.setStudent(updatedAdmission.getStudent());
        admission.setUniversity(updatedAdmission.getUniversity());
        admission.setCourse(updatedAdmission.getCourse());
        return repository.save(admission);
    }
}
