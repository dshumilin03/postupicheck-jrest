package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseConverter implements Converter<Course, CourseDto> , ListConverter<Course, CourseDto> {

    @Override
    public CourseDto convert(Course course) {
        long universityId = course
                        .getUniversity()
                        .getId();
        List<Long> subjectsId = course.getRequiredSubjects()
                .stream()
                .map(Subject::getId).toList();

        int curPassingPoints = course
                .getCurPassingPoints();
        int budgetPlaces = course
                .getBudgetPlaces();
        long id = course.getId();

        String name = course.getName();
        String code = course.getCode();

        return new CourseDto(
                id,
                name,
                code,
                universityId,
                subjectsId,
                curPassingPoints,
                budgetPlaces);
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
