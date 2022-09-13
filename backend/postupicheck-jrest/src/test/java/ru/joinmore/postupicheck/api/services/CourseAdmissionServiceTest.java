package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        course.setBudgetPlaces(2);
        course.setId(id);

        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course);

        Admission admission1 = new Admission();
        Admission admission2 = new Admission();
        Admission admission3 = new Admission();
        admission1.setPoints(120);
        admission2.setPoints(140);
        admission3.setPoints(250);

        List<Admission> courseAdmissions = new ArrayList<>();
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
        Course course = new Course();
        course.setBudgetPlaces(5);
        course.setId(145L);

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
        List<Course> allCourses = createCourses();
        Course course1 = allCourses.get(0);
        Course course2 = allCourses.get(1);
        List<Admission> courseAdmissions = createAdmissions();

        when(admissionService.getCourseAdmissions(course1)).thenReturn(courseAdmissions);
        when(admissionService.getCourseAdmissions(course2)).thenReturn(courseAdmissions);
        when(courseService.getAll()).thenReturn(allCourses);
        when(courseService.saveAll(allCourses)).thenReturn(allCourses);

        // when
        List<Course> result = testInstance.updateCourseCurPassingScore();

        // then
        assertThat(result).contains(course1, course2);
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

    @Test
    void shouldSaveUpdatedCourseAvailableBudgetPlaces() {
        // given
        List<Course> courses = createCourses();
        List<Admission> admissions = createAdmissions();
        when(courseService.getAll()).thenReturn(courses);
        when(admissionService.getCourseAdmissions(any(Course.class))).thenReturn(admissions);

        // when
        testInstance.updateCourseCurPassingScore();

        // then
        verify(courseService).saveAll(courses);
    }

    @Test
    void shouldUpdateAvailableBudgetPlaces() {
        // given
        List<Course> courses = createCourses();
        Course course1 = courses.get(0);
        Course course2 = courses.get(1);
        List<Admission> admissions = createAdmissions();
        Admission admission1 = admissions.get(1);
        Admission admission2 = admissions.get(2);
        Student student1 = admissions.get(1).getStudent();
        Student student2 = admissions.get(2).getStudent();
        when(courseService.getAll()).thenReturn(courses);
        Course anyCourse = any(Course.class);
        when(admissionService.getCourseAdmissions(anyCourse)).thenReturn(admissions);
        when(courseService.saveAll(courses)).thenReturn(courses);
        when(admissionService.getStudentAdmission(course1, student1)).thenReturn(admission1);
        when(admissionService.getStudentAdmission(course1, student2)).thenReturn(admission2);
        when(admissionService.getStudentAdmission(course2, student1)).thenReturn(admission1);
        when(admissionService.getStudentAdmission(course2, student2)).thenReturn(admission2);

        // when
        List<Course> result = testInstance.updateAvailablePlaces();

        // then
        assertThat(result.get(0).getAvailableBudgetPlaces()).isEqualTo(1);
        assertThat(result.get(1).getAvailableBudgetPlaces()).isEqualTo(4);
    }

    private List<Admission> createAdmissions() {
        List<Admission> courseAdmissions = new ArrayList<>();
        Admission admission1 = new Admission();
        Admission admission2 = new Admission();
        Admission admission3 = new Admission();

        Student student1 = new Student();
        student1.setPreferential(false);

        Student student2 = new Student();
        student2.setPreferential(true);

        Student student3 = new Student();
        student3.setPreferential(true);

        admission1.setPoints(250);
        admission1.setStudent(student1);
        admission1.setConsent(false);

        admission2.setPoints(140);
        admission2.setStudent(student2);
        admission2.setConsent(true);

        admission3.setPoints(120);
        admission3.setStudent(student3);
        admission3.setConsent(false);

        courseAdmissions.add(admission1);
        courseAdmissions.add(admission2);
        courseAdmissions.add(admission3);


        return courseAdmissions;
    }

    private List<Course> createCourses() {
        Course course1 = new Course();
        int budgetPlaces1 = 2;
        long id1 = 145L;
        course1.setBudgetPlaces(budgetPlaces1);
        course1.setAvailableBudgetPlaces(budgetPlaces1);
        course1.setId(id1);

        Course course2 = new Course();
        long id2 = 1435L;
        int budgetPlaces2 = 5;
        course2.setBudgetPlaces(budgetPlaces2);
        course2.setAvailableBudgetPlaces(budgetPlaces2);
        course2.setId(id2);

        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course1);
        allCourses.add(course2);

        return allCourses;
    }
}