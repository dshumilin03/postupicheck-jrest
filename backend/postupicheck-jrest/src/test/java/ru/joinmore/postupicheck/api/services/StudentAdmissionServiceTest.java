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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentAdmissionServiceTest {
    private StudentAdmissionService underTest;
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
        underTest = new StudentAdmissionService(
                studentService,
                courseService,
                studentExamResultService,
                admissionService);
    }

    @Test
    void getStudentAdmissions() {
        //given
        long id = 5;
        //when
        underTest.getStudentAdmissions(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(admissionService).findAdmissionsByStudentId(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(id);


    }

    @Test
    void getStudentConsentAdmission() {
        //given
        Student student = new Student("name", "123");
        Course course1 = new Course();
        Course course2 = new Course();
        Admission admission1 = new Admission(student, course1, true);
        Admission admission2 = new Admission(student, course2, false);
        List<Admission> studentAdmissions = new ArrayList<>();
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);
        long id = 5;
        given(admissionService.findAdmissionsByStudentId(id)).willReturn(studentAdmissions);
        //when
        Admission consent = underTest.getStudentConsentAdmission(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(admissionService).findAdmissionsByStudentId(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(id);
        assertThat(consent.isConsent()).isEqualTo(true);

    }

    @Test
    void getStudentAvailableAdmissions() {
        //given
        Student student = new Student("name", "123");
        Course course1 = new Course();
        course1.setCurPassingPoints(150);
        Course course2 = new Course();
        course2.setCurPassingPoints(300);
        Admission admission1 = new Admission(student, course1, true);
        Admission admission2 = new Admission(student, course2, false);
        List<Admission> studentAdmissions = new ArrayList<>();
        studentAdmissions.add(admission1);
        studentAdmissions.add(admission2);
        List<Subject> requiredSubjects = new ArrayList<>();
        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("ru");
        Subject subject3 = new Subject("inf");
        requiredSubjects.add(subject1);
        requiredSubjects.add(subject2);
        requiredSubjects.add(subject3);
        long id = 5;
        given(studentService.get(id)).willReturn(student);
        given(admissionService.findAdmissionsByStudentId(id)).willReturn(studentAdmissions);
        given(courseService.getRequiredSubjects(course1)).willReturn(requiredSubjects);
        given(courseService.getRequiredSubjects(course2)).willReturn(requiredSubjects);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).willReturn(50);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).willReturn(55);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).willReturn(60);

        //when
        List<Admission> availableAdmissions = underTest.getStudentAvailableAdmissions(id);
        int studentPoints = underTest.getStudentAdmissionPoints(student, admission1.getCourse());
        //then
        ArgumentCaptor<Long> longArgumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> longArgumentCaptor2 = ArgumentCaptor.forClass(Long.class);
        verify(studentService).get(longArgumentCaptor1.capture());
        long capturedLong = longArgumentCaptor1.getValue();
        assertThat(capturedLong).isEqualTo(id);
        verify(admissionService).findAdmissionsByStudentId(longArgumentCaptor2.capture());
        long capturedLong2 = longArgumentCaptor2.getValue();
        assertThat(capturedLong2).isEqualTo(id);
        assertThat(studentPoints).isEqualTo(165);
        availableAdmissions.forEach(admission -> {
            assertThat(admission.getCourse().getCurPassingPoints()).isLessThan(165);
        });
    }

    @Test
    void getStudentAdmissionPoints() {
        //given
        Student student = new Student("name", "123");
        Course course = new Course();
        List<Subject> requiredSubjects = new ArrayList<>();
        Subject subject1 = new Subject("math");
        Subject subject2 = new Subject("ru");
        Subject subject3 = new Subject("inf");
        requiredSubjects.add(subject1);
        requiredSubjects.add(subject2);
        requiredSubjects.add(subject3);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject1)).willReturn(50);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject2)).willReturn(55);
        given(studentExamResultService.getPointsByStudentAndSubject(student, subject3)).willReturn(60);
        given(courseService.getRequiredSubjects(course)).willReturn(requiredSubjects);
        //when
        int result = underTest.getStudentAdmissionPoints(student, course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).getRequiredSubjects(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertThat(capturedCourse).isEqualTo(course);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(studentExamResultService, times(3)).
                getPointsByStudentAndSubject(
                        studentArgumentCaptor.capture(),
                        subjectArgumentCaptor.capture());
        List<Student> capturedStudents = studentArgumentCaptor.getAllValues();
        List<Subject> capturedSubjects = subjectArgumentCaptor.getAllValues();
        capturedStudents.forEach(capturedStudent -> {
            assertThat(capturedStudent).isEqualTo(student);
        });
        assertThat(capturedSubjects.get(0)).isEqualTo(subject1);
        assertThat(capturedSubjects.get(1)).isEqualTo(subject2);
        assertThat(capturedSubjects.get(2)).isEqualTo(subject3);
        assertThat(result).isEqualTo(165);
    }
}