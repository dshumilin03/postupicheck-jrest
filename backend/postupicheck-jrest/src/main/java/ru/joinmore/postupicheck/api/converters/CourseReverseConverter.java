package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

@Component
public class CourseReverseConverter implements Converter<CourseDto, Course> {

    private final UniversityService universityService;
    private final SubjectService subjectService;

    public CourseReverseConverter(UniversityService universityService, SubjectService subjectService) {
        this.universityService = universityService;
        this.subjectService = subjectService;
    }

    @Override
    public Course convert(CourseDto courseDto) {

        String courseName = courseDto.getName();
        String courseCode = courseDto.getCode();

        long universityId = courseDto.getUniversityId();
        long firstSubjectId = courseDto.getFirstSubjectId();
        long secondSubjectId = courseDto.getSecondSubjectId();
        long thirdSubjectId = courseDto.getThirdSubjectId();
        long courseId = courseDto.getId();
        int curPassingPoints = courseDto.getCurPassingPoints();

        University university = universityService.get(universityId);
        Subject firstSubject = subjectService.get(firstSubjectId);
        Subject secondSubject = subjectService.get(secondSubjectId);
        Subject thirdSubject = subjectService.get(thirdSubjectId);

        return new Course(courseId, courseName, courseCode, university, firstSubject, secondSubject, thirdSubject, curPassingPoints);

    }
}
