package ru.joinmore.postupicheck.api.Factories;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.repositories.CourseRequiredSubjectRepository;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseFactory {

    private final CourseRequiredSubjectRepository courseRequiredSubjectRepository;
    private final CourseService courseService;
    private final SubjectService subjectService;

    public CourseFactory(
            CourseService courseService,
            CourseRequiredSubjectRepository courseRequiredSubjectRepository,
            SubjectService subjectService) {
        this.courseService = courseService;
        this.courseRequiredSubjectRepository = courseRequiredSubjectRepository;
        this.subjectService = subjectService;
    }

    public Course create(
            long courseId,
            String courseName,
            String courseCode,
            University university,
            int curPassingPoints,
            int budgetPlaces,
            List<Subject> requiredSubjects) {

        Course course = new Course(courseId, courseName, courseCode, university, curPassingPoints, budgetPlaces);
        Course createdCourse = courseService.create(course);

        createRequiredSubjects(requiredSubjects, createdCourse);

        return createdCourse;
    }

    public Course create(
            Course course,
            List<Long> requiredSubjectsId) {

        Course createdCourse = courseService.create(course);
        List<Subject> requiredSubjects = requiredSubjectsId.stream().map(subjectService::get).toList();

        createRequiredSubjects(requiredSubjects, createdCourse);

        return createdCourse;
    }

    private void createRequiredSubjects(List<Subject> requiredSubjects, Course course) {
        List<CourseRequiredSubject> courseRequiredSubjects = new ArrayList<>();
        requiredSubjects.forEach(subject -> {
            CourseRequiredSubject courseRequiredSubject = new CourseRequiredSubject(course, subject);
            courseRequiredSubjects.add(courseRequiredSubject);
        });

        courseRequiredSubjectRepository.saveAll(courseRequiredSubjects);
    }
}
