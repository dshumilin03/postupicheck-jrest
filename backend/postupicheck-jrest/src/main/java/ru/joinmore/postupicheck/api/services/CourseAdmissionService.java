package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;

import java.util.List;

@Service
public class CourseAdmissionService {

    private final CourseService courseService;
    private final AdmissionService admissionService;

    public CourseAdmissionService(
            CourseService courseService,
            AdmissionService admissionService) {
        this.courseService = courseService;
        this.admissionService = admissionService;
    }

    public List<Course> updateCourseCurPassingScore() {
        List<Course> courses = courseService.getAll();

        courses.forEach(course -> {
            List<Admission> courseAdmissions = admissionService.getCourseAdmissions(course);
            Admission lastAdmission;
            int budgetPlaces = course.getBudgetPlaces();
            int admissionsCount = courseAdmissions.size();

            if (budgetPlaces < admissionsCount) {
                lastAdmission = courseAdmissions.get(budgetPlaces - 1);
            } else {
                lastAdmission = courseAdmissions.get(admissionsCount - 1);
            }

            int newPassingScore = lastAdmission.getPoints();
            course.setCurPassingPoints(newPassingScore);
        });

        return courseService.saveAll(courses);
    }

    public List<Admission> getCourseAdmissions(long id) {
        Course course = courseService.get(id);

        return admissionService.getCourseAdmissions(course);
    }
}
