package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentExamResultRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentExamResultsResultServiceTest {

    @Mock
    private StudentExamResultRepository studentExamResultRepository;
    private StudentExamResultService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentExamResultService(studentExamResultRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(studentExamResultRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");

        StudentExamResult studentExamResult = new StudentExamResult(result, student, subject);
        studentExamResult.setId(id);

        given(studentExamResultRepository.findById(id)).willReturn(Optional.of(studentExamResult));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentExamResultRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");
        StudentExamResult studentExamResult = new StudentExamResult(result, student, subject);
        //when
        underTest.create(studentExamResult);
        //then
        ArgumentCaptor<StudentExamResult> studentExamResultArgumentCaptor = ArgumentCaptor.forClass(StudentExamResult.class);

        verify(studentExamResultRepository).save(studentExamResultArgumentCaptor.capture());

        StudentExamResult capturedStudentExamResult = studentExamResultArgumentCaptor.getValue();

        assertThat(capturedStudentExamResult).isEqualTo(studentExamResult);
    }

    @Test
    void createExistingStudentExamResult() {
        //given
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");
        StudentExamResult studentExamResult = new StudentExamResult(result, student, subject);
        //when
        given(studentExamResultRepository.existsBySubjectAndStudent(subject, student)).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(studentExamResult))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(studentExamResult.getSubject().getName());

        verify(studentExamResultRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        int oldResult = 100;
        int newResult = 80;

        Student oldStudent = new Student("testStudent", "123456");
        Student newStudent = new Student("testStudent2", "23456");

        Subject oldSubject = new Subject("testSubject");
        Subject newSubject = new Subject("testSubject2");

        StudentExamResult oldStudentExamResult = new StudentExamResult(oldResult, oldStudent, oldSubject);
        StudentExamResult newStudentExamResult = new StudentExamResult(newResult, newStudent, newSubject);
        long id = anyLong();

        given(studentExamResultRepository.findById(id)).willReturn(Optional.of(oldStudentExamResult));
        //when
        underTest.replace(newStudentExamResult, id);
        //then
        verify(studentExamResultRepository).findById(id);

        ArgumentCaptor<StudentExamResult> studentExamResultArgumentCaptor = ArgumentCaptor.forClass(StudentExamResult.class);

        verify(studentExamResultRepository).save(studentExamResultArgumentCaptor.capture());
        StudentExamResult capturedStudentExamResult = studentExamResultArgumentCaptor.getValue();

        assertThat(capturedStudentExamResult.getPoints()).isEqualTo(newStudentExamResult.getPoints());
        assertThat(capturedStudentExamResult.getStudent()).isEqualTo(newStudentExamResult.getStudent());
        assertThat(capturedStudentExamResult.getSubject()).isEqualTo(newStudentExamResult.getSubject());


    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentExamResultRepository).deleteById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingStudentExamResults() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(studentExamResultRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }

    @Test
    void getAllStudentResults() {
        //given
        Student student = new Student("name", "123");
        //when
        underTest.getAllStudentResults(student);
        //then
        ArgumentCaptor<Student> studentExamResultArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentExamResultRepository).findStudentExamResultsByStudent(studentExamResultArgumentCaptor.capture());
        Student capturedStudent = studentExamResultArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void getPointsByStudentAndSubject() {
        //given
        Student student = new Student("name", "123");
        Subject subject = new Subject("subjectName");
        //when
        underTest.getPointsByStudentAndSubject(student, subject);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(studentExamResultRepository).
                getPointsByStudentAndSubject(
                        studentArgumentCaptor.capture(),
                        subjectArgumentCaptor.capture()
                );
        Student capturedStudent = studentArgumentCaptor.getValue();
        Subject capturedSubject = subjectArgumentCaptor.getValue();
        assertThat(capturedSubject).isEqualTo(subject);
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void getAllStudentResultsByStudentId() {
        //given
        long id = 5;
        //when
        underTest.getAllStudentResultsByStudentId(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentExamResultRepository).findStudentExamResultsByStudent_Id(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(id);

    }
}