package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.exception.CourseNotFoundException;
import ru.joinmore.postupicheck.api.model.Course;
import ru.joinmore.postupicheck.api.repository.CourseRepository;

import java.util.List;

@RestController
public class CourseController {

    private final CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/courses")
    List<Course> all() {
        return repository.findAll();
    }

    @PostMapping("/courses")
    Course newCourse(@RequestBody Course course) {
        return repository.save(course);
    }

    @GetMapping("/course/{id}")
    Course one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));}

    @PutMapping("/courses/{id}")
    Course replaceCourse(@RequestBody Course updatedCourse, Long id) {
        return repository.findById(id) //
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    course.setCode(updatedCourse.getCode());
                    course.setUniversity(updatedCourse.getUniversity());
                    course.setFirstSubject(updatedCourse.getFirstSubject());
                    course.setSecondSubject(updatedCourse.getSecondSubject());
                    course.setThirdSubject(updatedCourse.getThirdSubject());
                    return repository.save(updatedCourse);
                })
                .orElseGet(() -> {
                    updatedCourse.setId(id);
                    return repository.save(updatedCourse);
                });
    }

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
