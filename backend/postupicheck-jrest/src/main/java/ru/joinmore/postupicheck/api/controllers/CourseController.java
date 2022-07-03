package ru.joinmore.postupicheck.api.controllers;

import org.springframework.web.bind.annotation.*;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.facades.CourseFacade;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseFacade courseFacade;

    public CourseController(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    @GetMapping
    List<CourseDto> getAllCourses() {
        return courseFacade.getAll();
    }

    @PostMapping
    CourseDto createCourse(@RequestBody CourseDto course) {
        return courseFacade.create(course);
    }

    @GetMapping("/{id}")
    CourseDto getCourse(@PathVariable Long id) {
        return courseFacade.get(id);
    }

    @PutMapping("/{id}")
    CourseDto replaceCourse(@RequestBody CourseDto updatedCourse, Long id) {
        return courseFacade.replace(updatedCourse, id);
    }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable Long id) {
        courseFacade.delete(id);
    }
}
