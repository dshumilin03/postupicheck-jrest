package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.repositories.StudentExamResultsRepository;

import java.util.List;

@Service
public class StudentExamResultsService {

    private final StudentExamResultsRepository repository;

    public StudentExamResultsService(StudentExamResultsRepository repository) {
        this.repository = repository;
    }

    public List<StudentExamResults> getAll() {
        return repository.findAll();
    }

    public StudentExamResults get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Result with id " + id));
    }

    public StudentExamResults create(StudentExamResults examResults) {
        Boolean exists = repository.existsBySubject(examResults.getSubject());

        if (exists) {
            throw new AlreadyExistsException(examResults.getSubject().getName() + " result");
        }
        return repository.save(examResults);
    }

    public StudentExamResults replace(StudentExamResults updatedExamResults, long id) {
        StudentExamResults examResults = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Result with id " + id));
        return replaceStudent(examResults, updatedExamResults);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Exam result with id " + id);
        }
    }

    private StudentExamResults replaceStudent(StudentExamResults examResults, StudentExamResults updatedExamResults) {
        examResults.setResult(updatedExamResults.getResult());
        examResults.setStudent(updatedExamResults.getStudent());
        examResults.setSubject(updatedExamResults.getSubject());
        return repository.save(examResults);
    }
}

