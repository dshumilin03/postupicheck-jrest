package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentForecastRepository;

import java.util.List;

@Service
public class StudentForecastService {

    private final StudentForecastRepository repository;

    public StudentForecastService(StudentForecastRepository repository) {
        this.repository = repository;
    }

    public List<StudentForecast> getAll() {
        return repository.findAll();
    }

    public StudentForecast get(long id) {
        return repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Student forecast with id [" + id + "]"));
    }

    public StudentForecast create(StudentForecast studentForecast) {
        Student student = studentForecast
                .getAdmission()
                .getStudent();
        Boolean exists = repository.existsStudentForecastByAdmissionStudent(student);

        if (exists) {
            String studentName = student.getName();
            String message = String.format("StudentForecast for student %s ", studentName);
            throw new AlreadyExistsException(message);
        }

        return repository.save(studentForecast);
    }

    public StudentForecast replace(StudentForecast updatedStudentForecast, long id) {
        StudentForecast studentForecast = repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Student forecast with id [" + id + "]"));

        return replaceStudentForecast(studentForecast, updatedStudentForecast);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Student forecast with id [" + id + "]");
        }
    }

    private StudentForecast replaceStudentForecast(StudentForecast studentForecast, StudentForecast updatedStudentForecast) {
        studentForecast.setAdmission(updatedStudentForecast.getAdmission());

        return repository.save(studentForecast);
    }

    public StudentForecast getStudentForecast(Long id) {
        StudentForecast forecast = repository.findStudentForecastByAdmissionStudentId(id);

        if (forecast == null) {
            throw new ResourceNotExistsException("Student forecast with id [" + id + "]");
        }

        return forecast;
    }
}
