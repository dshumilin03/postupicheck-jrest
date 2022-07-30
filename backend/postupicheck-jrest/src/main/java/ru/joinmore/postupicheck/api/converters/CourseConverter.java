package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseConverter implements Converter<Course, CourseDto> , ListConverter<Course, CourseDto> {

    @Override
    public CourseDto convert(Course course) {

        long universityId = course.getUniversity().getId();
        long firstSubjectId = course.getFirstSubject().getId();
        long secondSubjectId = course.getSecondSubject().getId();
        long thirdSubjectId = course.getThirdSubject().getId();
        int curPassingPoints = course.getCurPassingPoints();
        long id = course.getId();
        String name = course.getName();
        String code = course.getCode();

        return new CourseDto(
                id,
                name,
                code,
                universityId,
                firstSubjectId,
                secondSubjectId,
                thirdSubjectId,
                curPassingPoints);
    }

    @Override
    public List<CourseDto> convert(List<Course> courses) {
        List<CourseDto> courseDtoList = new ArrayList<>();
        courses.
                forEach(course -> {
                    CourseDto courseDto = convert(course);
                    courseDtoList.add(courseDto);
                });
        return courseDtoList;
    }
}
