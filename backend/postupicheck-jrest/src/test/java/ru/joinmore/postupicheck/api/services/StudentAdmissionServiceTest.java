package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.entities.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StudentAdmissionServiceTest {

    private StudentAdmissionService testInstance;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentExamResultService studentExamResultService;
    @Mock
    private AdmissionService admissionService;

    @BeforeEach
    void setUp() {
        testInstance = new StudentAdmissionService(
                studentService,
                studentExamResultService,
                admissionService);
    }

    @Test
    void shouldCallFindAdmissionsByStudentId() {
        // given
        long id = 5;

        // when
        testInstance.getStudentAdmissions(id);

        // then
        verify(admissionService).findAdmissionsByStudentId(id);
    }

    @Test
    void shouldReturnAdmissions_WhenFindAdmissionsByStudentId() {
        // given
        long id = 5;
        List<Admission> admissions = createAdmissionList();
        Admission admission1 = admissions.get(0);
        Admission admission2 = admissions.get(1);
        Admission admission3 = admissions.get(2);

        when(admissionService.findAdmissionsByStudentId(id)).thenReturn(admissions);

        // when
        List<Admission> result = testInstance.getStudentAdmissions(id);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldReturnStudentConsentAdmission() {
        // given
        long studentId = 5;

        Admission admission1 = new Admission();
        admission1.setConsent(true);

        Admission admission2 = new Admission();
        admission2.setConsent(false);

        Admission admission3 = new Admission();
        admission3.setConsent(false);

        List<Admission> studentAdmissions = new ArrayList<>();
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);
        studentAdmissions.add(admission3);
        when(admissionService.findAdmissionsByStudentId(studentId)).thenReturn(studentAdmissions);

        // when
        Admission result = testInstance.getStudentConsentAdmission(studentId);

        // then
        assertThat(result.isConsent()).isEqualTo(true);
    }

    @Test
    void shouldReturnStudentAvailableAdmissions() {
        // given
        long studentId = 5;

        Course course1 = new Course();
        course1.setCurPassingPoints(150);

        Course course2 = new Course();
        course2.setCurPassingPoints(300);

        Student student = mock(Student.class);
        int points1 = 213;
        int points2 = 214;
        Admission admission1 = new Admission(student, course1, true, points1);
        Admission admission2 = new Admission(student, course2, false, points2);

        List<Admission> studentAdmissions = new ArrayList<>();
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);

        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);

        List<CourseRequiredSubject> requiredSubjects =
                createCourseRequiredSubjects(course1, subject1, subject2, subject3);

        List<CourseRequiredSubject> requiredSubjects2 =
                createCourseRequiredSubjects(course2, subject1, subject2, subject3);

        course1.setRequiredSubjects(requiredSubjects);
        course2.setRequiredSubjects(requiredSubjects2);

        when(studentService.get(studentId)).thenReturn(student);
        when(admissionService.findAdmissionsByStudentId(studentId)).thenReturn(studentAdmissions);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).thenReturn(50);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).thenReturn(55);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).thenReturn(60);

        // when
        List<Admission> result = testInstance.getStudentAvailableAdmissions(studentId);

        // then
        result.forEach(admission ->
                assertThat(admission
                        .getCourse()
                        .getCurPassingPoints())
                        .isLessThan(165));
    }

    @Test
    void shouldGetStudentAdmissionPoints() {
        // given
        Student student = mock(Student.class);
        Course course = new Course();
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);

        List<CourseRequiredSubject> requiredSubjects
                = createCourseRequiredSubjects(course, subject1, subject2, subject3);

        course.setRequiredSubjects(requiredSubjects);

        when(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).thenReturn(50);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).thenReturn(55);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).thenReturn(60);

        // when
        int result = testInstance.getStudentAdmissionPoints(student, course);

        // then
        assertThat(result).isEqualTo(165);
    }

    private List<Admission> createAdmissionList() {
        List<Admission> admissions = new ArrayList<>();
        Admission admission1 = mock(Admission.class);
        Admission admission2 = mock(Admission.class);
        Admission admission3 = mock(Admission.class);

        admissions.add(admission1);
        admissions.add(admission2);
        admissions.add(admission3);

        return admissions;
    }

    private List<CourseRequiredSubject> createCourseRequiredSubjects(
            Course course,
            Subject subject1,
            Subject subject2,
            Subject subject3) {
        CourseRequiredSubject courseRequiredSubject1 = new CourseRequiredSubject(course, subject1);
        CourseRequiredSubject courseRequiredSubject2 = new CourseRequiredSubject(course, subject2);
        CourseRequiredSubject courseRequiredSubject3 = new CourseRequiredSubject(course, subject3);

        List<CourseRequiredSubject> courseRequiredSubjects = new ArrayList<>();
        courseRequiredSubjects.add(courseRequiredSubject1);
        courseRequiredSubjects.add(courseRequiredSubject2);
        courseRequiredSubjects.add(courseRequiredSubject3);

        return courseRequiredSubjects;
    }
}