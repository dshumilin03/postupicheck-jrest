package ru.joinmore.postupicheck.api.Factories;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.List;

@Component
public class CourseFactory {

    private final CourseService courseService;
    private final SubjectService subjectService;

    public CourseFactory(
            CourseService courseService,
            SubjectService subjectService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
    }

    public Course create(
            Course course,
            List<Long> requiredSubjectsId) {

        List<Subject> requiredSubjects = requiredSubjectsId.stream().map(subjectService::get).toList();

        Course createdCourse = courseService.create(course);
        Long createdCourseId = createdCourse.getId();

        List<CourseRequiredSubject> courseRequiredSubjects = courseService
                .createAndSaveRequiredSubjects(requiredSubjects, createdCourse);
        course.setRequiredSubjects(courseRequiredSubjects);
        createdCourse.setRequiredSubjects(courseRequiredSubjects);

        return courseService.get(createdCourseId);
    }

    // is it necessary?
    public Course create(
            long courseId,
            String courseName,
            String courseCode,
            University university,
            int curPassingPoints,
            int budgetPlaces,
            List<Subject> requiredSubjects) {

        Course course = new Course(courseId, courseName, courseCode, university, curPassingPoints, budgetPlaces);

        List<CourseRequiredSubject> courseRequiredSubjects = courseService
                .createAndSaveRequiredSubjects(requiredSubjects, course);
        course.setRequiredSubjects(courseRequiredSubjects);

        return courseService.create(course);
    }
}
