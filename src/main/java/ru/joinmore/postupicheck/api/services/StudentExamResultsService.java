package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotFoundException;
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
                .orElseThrow(ResourceNotFoundException::new);
    }

    public StudentExamResults create(StudentExamResults examResults) {
        return repository.save(examResults);
    }

    public StudentExamResults replace(StudentExamResults updatedExamResults, long id) {
        StudentExamResults examResults = repository.findById(id) //
                .orElseThrow(ResourceNotFoundException::new);
        return replaceStudent(examResults, updatedExamResults);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private StudentExamResults replaceStudent(StudentExamResults examResults, StudentExamResults updatedExamResults) {
        examResults.setResult(updatedExamResults.getResult());
        examResults.setStudent(updatedExamResults.getStudent());
        examResults.setSubject(updatedExamResults.getSubject());
        return repository.save(examResults);
    }
}

