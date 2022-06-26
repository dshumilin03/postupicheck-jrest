package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.UniversityNotFoundException;
import ru.joinmore.postupicheck.api.model.University;
import ru.joinmore.postupicheck.api.repository.UniversityRepository;

import java.util.List;

@Service
public class UniversityService {

    private final UniversityRepository repository;

    public UniversityService(UniversityRepository repository) {
        this.repository = repository;
    }

    public List<University> getAllUniversities() {
        return repository.findAll();
    }

    public University getUniversity(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new UniversityNotFoundException(id));
    }

    public University createUniversity(University university) {
        return repository.save(university);
    }

    public University replaceUniversity(University updatedUniversity, Long id) {
        University university = repository.findById(id) //
                .orElseThrow(() -> new UniversityNotFoundException(id));
        return replaceUniversity(university, updatedUniversity);
    }

    public void deleteUniversity(Long id) {
        repository.deleteById(id);
    }

    private University replaceUniversity(University university, University updatedUniversity) {
        university.setName(updatedUniversity.getName());
        return repository.save(updatedUniversity);
    }
}
