package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotFoundException;
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
                .orElseThrow(ResourceNotFoundException::new);
    }

    public Course create(Course course) {
        return repository.save(course);
    }

    public Course replace(Course updatedCourse, long id) {
        Course course = repository.findById(id) //
                .orElseThrow(ResourceNotFoundException::new);
        return replaceCourse(course, updatedCourse);
    }

    public void delete(long id) {
        repository.deleteById(id);
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
