package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.repositories.StudentExamResultRepository;

import java.util.List;

@Service
public class StudentExamResultService {

    private final StudentExamResultRepository repository;

    public StudentExamResultService(StudentExamResultRepository repository) {
        this.repository = repository;
    }

    public List<StudentExamResult> getAll() {
        return repository.findAll();
    }

    public StudentExamResult get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Result with id " + id));
    }

    public StudentExamResult create(StudentExamResult studentExamResult) {
        Subject subject = studentExamResult.getSubject();
        Student student = studentExamResult.getStudent();
        Boolean exists = repository.existsBySubjectAndStudent(subject, student);

        if (exists) {
            throw new AlreadyExistsException(subject.getName() + " result for " + student.getName());
        }
        return repository.save(studentExamResult);
    }

    public StudentExamResult replace(StudentExamResult updatedStudentExamResult, long id) {
        StudentExamResult studentExamResult = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Result with id " + id));
        return replaceStudent(studentExamResult, updatedStudentExamResult);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Exam result with id " + id);
        }
    }

    private StudentExamResult replaceStudent(
            StudentExamResult studentExamResult,
            StudentExamResult updatedStudentExamResult) {
        studentExamResult.setPoints(updatedStudentExamResult.getPoints());
        studentExamResult.setStudent(updatedStudentExamResult.getStudent());
        studentExamResult.setSubject(updatedStudentExamResult.getSubject());
        return repository.save(studentExamResult);
    }

    public int getPointsByStudentAndSubject(Student student, Subject subject) {
        return repository.getPointsByStudentAndSubject(student, subject);
    }

    public List<StudentExamResult> getAllStudentResults(Student student) {
        return repository.findStudentExamResultsByStudent(student);
    }

    public List<StudentExamResult> getAllStudentResultsByStudentId(Long id) {
        return repository.findStudentExamResultsByStudent_Id(id);
    }
}

