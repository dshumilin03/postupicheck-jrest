package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
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
        return repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Subject with id [" + id + "]"));
    }

    public Subject create(Subject subject) {
        String subjectName = subject.getName();
        Boolean exists = repository.existsByName(subjectName);

        if (exists) {
            throw new AlreadyExistsException(subjectName);
        }

        return repository.save(subject);
    }

    public Subject replace(Subject updatedSubject, long id) {
        Subject subject = repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Subject with id [" + id + "]"));

        return replaceSubject(subject, updatedSubject);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Subject with id [" + id + "]");
        }
    }

    public Subject findByName(String name) {
        return repository.findByName(name);
    }

    private Subject replaceSubject(Subject subject, Subject updatedSubject) {
        subject.setName(updatedSubject.getName());

        return repository.save(subject);
    }
}
