package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.StudentExamResultsNotFoundException;
import ru.joinmore.postupicheck.api.model.StudentExamResults;
import ru.joinmore.postupicheck.api.repository.StudentExamResultsRepository;

import java.util.List;

@Service
public class StudentExamResultsService {

    private final StudentExamResultsRepository repository;

    public StudentExamResultsService(StudentExamResultsRepository repository) {
        this.repository = repository;
    }

    public List<StudentExamResults> getAllResults() {
        return repository.findAll();
    }

    public StudentExamResults getResult(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new StudentExamResultsNotFoundException(id));
    }

    public StudentExamResults createStudentExamResults(StudentExamResults examResults) {
        return repository.save(examResults);
    }

    public StudentExamResults replaceExamResults(StudentExamResults updatedExamResults, Long id) {
        StudentExamResults examResults = repository.findById(id) //
                .orElseThrow(() -> new StudentExamResultsNotFoundException(id));
        return replaceStudent(examResults, updatedExamResults);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    private StudentExamResults replaceStudent(StudentExamResults examResults, StudentExamResults updatedExamResults) {
        examResults.setResult(updatedExamResults.getResult());
        examResults.setStudent(updatedExamResults.getStudent());
        examResults.setSubject(updatedExamResults.getSubject());
        return repository.save(updatedExamResults);
    }
}

