package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.CourseConverter;
import ru.joinmore.postupicheck.api.converters.CourseReverseConverter;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseFacade {

    private final CourseService courseService;
    private final SubjectService subjectService;
    private final UniversityService universityService;
    private final CourseConverter converter;
    private final CourseReverseConverter reverseConverter;

    public CourseFacade(CourseService courseService,
                        SubjectService subjectService,
                        UniversityService universityService,
                        CourseConverter converter,
                        CourseReverseConverter reverseConverter) {
        this.courseService = courseService;
        this.subjectService = subjectService;
        this.universityService = universityService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    public CourseDto get(long id) {

        Course course = courseService.get(id);

        return converter.convert(course);
    }

    public List<CourseDto> getAll() {

        List<Course> courseList = courseService.getAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        courseList.
                forEach(course -> {
                    CourseDto courseDto = converter.convert(course);
                    courseDtoList.add(courseDto);
                });

        return courseDtoList;
    }

    public CourseDto create(CourseDto newCourseDto) {

        Course newCourse = reverseConverter.convert(newCourseDto);
        Course createdCourse = courseService.create(newCourse);

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

}
