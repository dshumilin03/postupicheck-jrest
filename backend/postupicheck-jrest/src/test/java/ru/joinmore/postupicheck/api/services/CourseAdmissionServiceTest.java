package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseAdmissionServiceTest {

    @Mock
    private CourseService courseService;
    @Mock
    private AdmissionService admissionService;
    private CourseAdmissionService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new CourseAdmissionService(courseService, admissionService);
    }

    @Test
    void shouldSetCurPassingScoreToLastBudgetPlace() {
        // given
        long id = 145L;
        Course course = new Course();
        int budgetPlaces = 2;
        course.setBudgetPlaces(budgetPlaces);
        course.setId(id);
        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course);
        List<Admission> courseAdmissions = new ArrayList<>();
        Admission admission1 = new Admission();
        Admission admission2 = new Admission();
        Admission admission3 = new Admission();
        admission1.setPoints(120);
        admission2.setPoints(140);
        admission3.setPoints(250);
        courseAdmissions.add(admission1);
        courseAdmissions.add(admission2);
        courseAdmissions.add(admission3);
        when(admissionService.getCourseAdmissions(course)).thenReturn(courseAdmissions);
        when(courseService.getAll()).thenReturn(allCourses);

        // when
        testInstance.updateCourseCurPassingScore();

        // then
        assertThat(course.getCurPassingPoints()).isEqualTo(140);

    }

    @Test
    void shouldSetCurPassingScoreToLastCoursePlace() {
        // given
        long id = 145L;
        Course course = new Course();
        int budgetPlaces = 5;
        course.setBudgetPlaces(budgetPlaces);
        course.setId(id);
        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course);
        List<Admission> courseAdmissions = createAdmissions();
        when(admissionService.getCourseAdmissions(course)).thenReturn(courseAdmissions);
        when(courseService.getAll()).thenReturn(allCourses);

        // when
        testInstance.updateCourseCurPassingScore();

        // then
        assertThat(course.getCurPassingPoints()).isEqualTo(120);

    }

    @Test
    void shouldReturnUpdatedCourses() {
        // given
        long id1 = 145L;
        long id2 = 1435L;
        int budgetPlaces1 = 2;
        int budgetPlaces2 = 5;
        Course course1 = new Course();
        course1.setBudgetPlaces(budgetPlaces1);
        course1.setId(id1);
        Course course2 = new Course();
        course2.setBudgetPlaces(budgetPlaces2);
        course2.setId(id2);
        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course1);
        allCourses.add(course2);
        List<Admission> courseAdmissions = createAdmissions();
        Course updatedCourse1 = mock(Course.class);
        Course updatedCourse2 = mock(Course.class);
        List<Course> updatedCourses = new ArrayList<>();
        updatedCourses.add(updatedCourse1);
        updatedCourses.add(updatedCourse2);
        when(admissionService.getCourseAdmissions(course1)).thenReturn(courseAdmissions);
        when(admissionService.getCourseAdmissions(course2)).thenReturn(courseAdmissions);
        when(courseService.getAll()).thenReturn(allCourses);
        when(courseService.replace(course1, id1)).thenReturn(updatedCourse1);
        when(courseService.replace(course2, id2)).thenReturn(updatedCourse2);

        // when
        List<Course> result = testInstance.updateCourseCurPassingScore();

        // then
        assertThat(result).isEqualTo(updatedCourses);

    }

    @Test
    void shouldReturnCourseAdmissions() {
        // given
        long id = 135L;
        Course course = mock(Course.class);
        List<Admission> courseAdmissions = createAdmissions();
        when(courseService.get(id)).thenReturn(course);
        when(admissionService.getCourseAdmissions(course)).thenReturn(courseAdmissions);

        // when
        List<Admission> result = testInstance.getCourseAdmissions(id);

        // then
        assertThat(result).isEqualTo(courseAdmissions);
    }

    private List<Admission> createAdmissions() {
        List<Admission> courseAdmissions = new ArrayList<>();
        Admission admission1 = new Admission();
        Admission admission2 = new Admission();
        Admission admission3 = new Admission();
        admission1.setPoints(250);
        admission2.setPoints(140);
        admission3.setPoints(120);
        courseAdmissions.add(admission1);
        courseAdmissions.add(admission2);
        courseAdmissions.add(admission3);
        return courseAdmissions;
    }
}