package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> getAll() {
        return repository.findAll();
    }

    public Course get(long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Course with id " + id));
    }

    public Course create(Course course) {

        String code = course.getCode();
        Boolean exists = repository.existsByCode(code);

        if (exists) {
            throw new AlreadyExistsException("Course with code " + code);
        }

        return repository.save(course);
    }

    public Course replace(Course updatedCourse, long id) {
        Course course = repository.findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Course with id " + id));
        return replaceCourse(course, updatedCourse);
    }

    public void delete(long id) {

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Course with id " + id);
        }
    }

    private Course replaceCourse(Course course, Course updatedCourse) {
        course.setName(updatedCourse.getName());
        course.setCode(updatedCourse.getCode());
        course.setUniversity(updatedCourse.getUniversity());
        course.setFirstSubject(updatedCourse.getFirstSubject());
        course.setSecondSubject(updatedCourse.getSecondSubject());
        course.setThirdSubject(updatedCourse.getThirdSubject());
        return repository.save(course);
    }
}
