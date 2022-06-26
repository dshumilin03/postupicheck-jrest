package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.SubjectNotFoundException;
import ru.joinmore.postupicheck.api.model.Subject;
import ru.joinmore.postupicheck.api.repository.SubjectRepository;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    public Subject getSubject(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public Subject createSubject(Subject subject) {
        return repository.save(subject);
    }

    public Subject replaceSubject(Subject updatedSubject, Long id) {
        Subject subject = repository.findById(id) //
                .orElseThrow(() -> new SubjectNotFoundException(id));
        return replaceSubject(subject, updatedSubject);
    }

    public void deleteSubject(Long id) {
        repository.deleteById(id);
    }

    private Subject replaceSubject(Subject subject, Subject updatedSubject) {
        subject.setName(updatedSubject.getName());
        return repository.save(updatedSubject);
    }
}
