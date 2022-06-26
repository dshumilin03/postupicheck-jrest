package ru.joinmore.postupicheck.api.service;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.exception.CourseNotFoundException;
import ru.joinmore.postupicheck.api.model.Course;
import ru.joinmore.postupicheck.api.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourse(Long id) {
        return repository.findById(id) //
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course createCourse(Course course) {
        return repository.save(course);
    }

    public Course replaceCourse(Course updatedCourse, Long id) {
        Course course = repository.findById(id) //
                .orElseThrow(() -> new CourseNotFoundException(id));
        return replaceCourse(course, updatedCourse);
    }

    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }

    private Course replaceCourse(Course course, Course updatedCourse) {
        course.setName(updatedCourse.getName());
        course.setCode(updatedCourse.getCode());
        course.setUniversity(updatedCourse.getUniversity());
        course.setFirstSubject(updatedCourse.getFirstSubject());
        course.setSecondSubject(updatedCourse.getSecondSubject());
        course.setThirdSubject(updatedCourse.getThirdSubject());
        return repository.save(updatedCourse);
    }
}
