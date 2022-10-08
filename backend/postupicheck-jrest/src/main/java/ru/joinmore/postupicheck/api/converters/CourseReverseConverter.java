package ru.joinmore.postupicheck.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.Factories.CourseFactory;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.repositories.CourseRequiredSubjectRepository;
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseReverseConverter implements Converter<CourseDto, Course> {

    private final UniversityService universityService;
    private final SubjectService subjectService;
    private final CourseRequiredSubjectRepository courseRequiredSubjectRepository;
    private final CourseFactory factory;

    public CourseReverseConverter(UniversityService universityService,
                                  SubjectService subjectService,
                                  CourseRequiredSubjectRepository courseRequiredSubjectRepository,
                                  CourseFactory factory) {
        this.universityService = universityService;
        this.subjectService = subjectService;
        this.courseRequiredSubjectRepository = courseRequiredSubjectRepository;
        this.factory = factory;
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
