package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.Factories.CourseFactory;
import ru.joinmore.postupicheck.api.converters.CourseConverter;
import ru.joinmore.postupicheck.api.converters.CourseReverseConverter;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.services.CourseService;

import java.util.List;

@Component
public class CourseFacade {

    private final CourseService courseService;
    private final CourseConverter converter;
    private final CourseReverseConverter reverseConverter;
    private final CourseFactory courseFactory;

    public CourseFacade(
            CourseService courseService,
            CourseConverter converter,
            CourseReverseConverter reverseConverter,
            CourseFactory courseFactory) {
        this.courseService = courseService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
        this.courseFactory = courseFactory;
    }

    public CourseDto get(long id) {
        Course course = courseService.get(id);

        return converter.convert(course);
    }

    public List<CourseDto> getAll() {
        List<Course> courseList = courseService.getAll();

        return converter.convert(courseList);
    }

    public CourseDto create(CourseDto newCourseDto) {
        Course newCourse = reverseConverter.convert(newCourseDto);
        Course createdCourse = courseFactory.create(newCourse, newCourseDto.getSubjectsId());

        return converter.convert(createdCourse);
    }

    public CourseDto replace(CourseDto updatedCourseDto, long id) {
        Course updatedCourse = reverseConverter.convert(updatedCourseDto);
        Course newCourse = courseService.replace(updatedCourse, id);

        return converter.convert(newCourse);
    }

    public void delete(long id) {
        courseService.delete(id);
    }

    public void deleteAll() {
        courseService.deleteAll();
    }
}
