package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.repositories.CourseRequiredSubjectRepository;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.List;

@Component
public class CourseReverseConverter implements Converter<CourseDto, Course> {

    private final UniversityService universityService;

    public CourseReverseConverter(
            UniversityService universityService) {
        this.universityService = universityService;
    }

    @Override
    public Course convert(CourseDto courseDto) {
        String courseName = courseDto.getName();
        String courseCode = courseDto.getCode();

        long universityId = courseDto.getUniversityId();
        long courseId = courseDto.getId();
        int curPassingPoints = courseDto.getCurPassingPoints();
        int budgetPlaces = courseDto.getBudgetPlaces();

        University university = universityService.get(universityId);

        return new Course(
                courseId,
                courseName,
                courseCode,
                university,
                curPassingPoints,
                budgetPlaces);
    }
}
