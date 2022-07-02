package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotFoundException;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.repositories.SubjectRepository;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public List<Subject> getAll() {
        return repository.findAll();
    }

    public Subject get(long id) {
        return repository.findById(id) //
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Subject create(Subject subject) {
        return repository.save(subject);
    }

    public Subject replace(Subject updatedSubject, long id) {
        Subject subject = repository.findById(id) //
                .orElseThrow(ResourceNotFoundException::new);
        return replaceSubject(subject, updatedSubject);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private Subject replaceSubject(Subject subject, Subject updatedSubject) {
        subject.setName(updatedSubject.getName());
        return repository.save(subject);
    }
}
