package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.models.Course;
import ru.joinmore.postupicheck.api.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService service) {
        this.courseService = service;
    }

    @GetMapping
    List<Course> getAllCourses() {
        return courseService.getAll();
    }

    @PostMapping
    Course createCourse(@RequestBody Course course) {
        return courseService.create(course);
    }

    @GetMapping("/{id}")
    Course getCourse(@PathVariable Long id) {
        return courseService.get(id);
    }

    @PutMapping("/{id}")
    Course replaceCourse(@RequestBody Course updatedCourse, Long id) {
        return courseService.replace(updatedCourse, id);
    }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
    }
}
