package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;

@Component
public class CourseConverter implements Converter<Course, CourseDto> {

    @Override
    public CourseDto convert(Course course) {

        long universityId = course.getUniversity().getId();
        long firstSubjectId = course.getFirstSubject().getId();
        long secondSubjectId = course.getSecondSubject().getId();
        long thirdSubjectId = course.getThirdSubject().getId();
        long id = course.getId();
        String name = course.getName();
        String code = course.getCode();

        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setCode(code);
        courseDto.setUniversityId(universityId);
        courseDto.setFirstSubjectId(firstSubjectId);
        courseDto.setSecondSubjectId(secondSubjectId);
        courseDto.setThirdSubjectId(thirdSubjectId);

        return courseDto;
    }
}