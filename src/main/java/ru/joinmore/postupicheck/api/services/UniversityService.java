package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exceptions.UniversityNotFoundException;
import ru.joinmore.postupicheck.api.models.University;
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
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public University create(University university) {
        return repository.save(university);
    }

    public University replace(University updatedUniversity, long id) {
        University university = repository.findById(id) //
                .orElseThrow(() -> new UniversityNotFoundException(id));
        return replaceUniversity(university, updatedUniversity);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    private University replaceUniversity(University university, University updatedUniversity) {
        university.setName(updatedUniversity.getName());
        return repository.save(university);
    }
}
