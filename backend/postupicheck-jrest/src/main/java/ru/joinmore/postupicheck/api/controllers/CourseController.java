package ru.joinmore.postupicheck.api.controllers;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code = HttpStatus.OK)
    List<CourseDto> getAllCourses() {
        return courseFacade.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    CourseDto createCourse(@RequestBody CourseDto course) {
        return courseFacade.create(course);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    CourseDto getCourse(@PathVariable Long id) {
        return courseFacade.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    CourseDto replaceCourse(@RequestBody CourseDto updatedCourse,
                            @PathVariable Long id) {
        return courseFacade.replace(updatedCourse, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteCourse(@PathVariable Long id) {
        courseFacade.delete(id);
    }

    @DeleteMapping()
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteCourses() {
        courseFacade.deleteAll();
    }

}

