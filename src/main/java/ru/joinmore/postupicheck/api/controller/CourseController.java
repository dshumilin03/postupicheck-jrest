package ru.joinmore.postupicheck.api.controller;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.model.Course;
import ru.joinmore.postupicheck.api.service.CourseService;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping("/courses")
    List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    @PostMapping("/courses")
    Course createCourse(@RequestBody Course course) {
        return service.createCourse(course);
    }

    @GetMapping("/course/{id}")
    Course getCourse(@PathVariable Long id) {
        return service.getCourse(id);
    }

    @PutMapping("/courses/{id}")
    Course replaceCourse(@RequestBody Course updatedCourse, Long id) {
        return service.replaceCourse(updatedCourse, id);
    }

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable Long id) {
        service.deleteCourse(id);
    }
}
