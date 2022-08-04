package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.repositories.UniversityRepository;

import java.util.List;

@Service
public class UniversityService {

    private final UniversityRepository repository;

    public UniversityService(UniversityRepository repository) {
        this.repository = repository;
    }

    public List<University> getAll() {
        return repository.findAll();
    }

    public University get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("University with id [" + id + "]"));
    }

    public University create(University university) {
        String universityName = university.getName();
        Boolean exists = repository.existsByName(universityName);
        if (exists) {
            throw new AlreadyExistsException(universityName);
        }
        return repository.save(university);
    }

    public University replace(University updatedUniversity, long id) {
        University university = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("University with id [" + id + "]"));
        return replaceUniversity(university, updatedUniversity);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("University with id [" + id + "]");
        }
    }

    private University replaceUniversity(University university, University updatedUniversity) {
        university.setName(updatedUniversity.getName());
        return repository.save(university);
    }
}
