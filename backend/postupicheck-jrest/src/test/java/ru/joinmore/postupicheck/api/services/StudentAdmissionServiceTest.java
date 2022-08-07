package ru.joinmore.postupicheck.api.services;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
    private CourseService courseService;
    @Mock
    private AdmissionService admissionService;

    @BeforeEach
    void setUp() {
        testInstance = new StudentAdmissionService(
                studentService,
                courseService,
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
        List<Admission> studentAdmissions = new ArrayList<>();
        Admission admission1 = new Admission();
        Admission admission2 = new Admission();
        Admission admission3 = new Admission();
        admission1.setConsent(true);
        admission2.setConsent(false);
        admission3.setConsent(false);
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);
        studentAdmissions.add(admission3);
        long id = 5;
        when(admissionService.findAdmissionsByStudentId(id)).thenReturn(studentAdmissions);

        // when
        Admission result = testInstance.getStudentConsentAdmission(id);

        // then
        assertThat(result.isConsent()).isEqualTo(true);

    }

    @Test
    void shouldReturnStudentAvailableAdmissions() {
        // given
        long id = 5;
        Student student = mock(Student.class);
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setCurPassingPoints(150);
        course2.setCurPassingPoints(300);
        int points1 = 213;
        int points2 = 214;
        Admission admission1 = new Admission(student, course1, true, points1);
        Admission admission2 = new Admission(student, course2, false, points2);
        List<Admission> studentAdmissions = new ArrayList<>();
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);
        List<Subject> requiredSubjects = new ArrayList<>();
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);
        requiredSubjects.add(subject1);
        requiredSubjects.add(subject2);
        requiredSubjects.add(subject3);
        when(studentService.get(id)).thenReturn(student);
        when(admissionService.findAdmissionsByStudentId(id)).thenReturn(studentAdmissions);
        when(courseService.getRequiredSubjects(course1)).thenReturn(requiredSubjects);
        when(courseService.getRequiredSubjects(course2)).thenReturn(requiredSubjects);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).thenReturn(50);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).thenReturn(55);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).thenReturn(60);
        when(courseService.getRequiredSubjects(course1)).thenReturn(requiredSubjects);
        when(courseService.getRequiredSubjects(course2)).thenReturn(requiredSubjects);

//        StudentAdmissionService testSpy = spy(testInstance);
//        doReturn(165).when(testSpy).getStudentAdmissionPoints(student, course1);
//        doReturn(165).when(testSpy).getStudentAdmissionPoints(student, course2);
//        when(testSpy.getStudentAdmissionPoints(student, course1)).thenReturn(165);
//        when(testSpy.getStudentAdmissionPoints(student, course2)).thenReturn(165);

        // when
        List<Admission> result = testInstance.getStudentAvailableAdmissions(id);

        // then
        result.forEach(admission -> {
            assertThat(admission.getCourse().getCurPassingPoints()).isLessThan(165);
        });
    }

    @Test
    void shouldGetStudentAdmissionPoints() {
        // given
        Student student = mock(Student.class);
        Course course = mock(Course.class);
        List<Subject> requiredSubjects = new ArrayList<>();
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);
        requiredSubjects.add(subject1);
        requiredSubjects.add(subject2);
        requiredSubjects.add(subject3);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).thenReturn(50);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).thenReturn(55);
        when(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).thenReturn(60);
        when(courseService.getRequiredSubjects(course)).thenReturn(requiredSubjects);

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
}