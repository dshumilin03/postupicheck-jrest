package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentExamResultsRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentExamResultsResultsServiceTest {

    @Mock
    private StudentExamResultsRepository studentExamResultsRepository;
    private StudentExamResultsService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentExamResultsService(studentExamResultsRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(studentExamResultsRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");

        StudentExamResults studentExamResults = new StudentExamResults(result, student, subject);
        studentExamResults.setId(id);

        given(studentExamResultsRepository.findById(id)).willReturn(Optional.of(studentExamResults));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentExamResultsRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");
        StudentExamResults studentExamResults = new StudentExamResults(result, student, subject);
        //when
        underTest.create(studentExamResults);
        //then
        ArgumentCaptor<StudentExamResults> studentExamResultsArgumentCaptor = ArgumentCaptor.forClass(StudentExamResults.class);

        verify(studentExamResultsRepository).save(studentExamResultsArgumentCaptor.capture());

        StudentExamResults capturedStudentExamResults = studentExamResultsArgumentCaptor.getValue();

        assertThat(capturedStudentExamResults).isEqualTo(studentExamResults);
    }

    @Test
    void createExistingStudentExamResults() {
        //given
        int result = 100;
        Student student = new Student("testStudent", "123456");
        Subject subject = new Subject("testSubject");
        StudentExamResults studentExamResults = new StudentExamResults(result, student, subject);
        //when
        given(studentExamResultsRepository.existsBySubject(subject)).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(studentExamResults))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(studentExamResults.getSubject().getName());

        verify(studentExamResultsRepository, never()).save(any());
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

        StudentExamResults oldStudentExamResults = new StudentExamResults(oldResult, oldStudent, oldSubject);
        StudentExamResults newStudentExamResults = new StudentExamResults(newResult, newStudent, newSubject);
        long id = anyLong();

        given(studentExamResultsRepository.findById(id)).willReturn(Optional.of(oldStudentExamResults));
        //when
        underTest.replace(newStudentExamResults, id);
        //then
        verify(studentExamResultsRepository).findById(id);

        ArgumentCaptor<StudentExamResults> studentExamResultsArgumentCaptor = ArgumentCaptor.forClass(StudentExamResults.class);

        verify(studentExamResultsRepository).save(studentExamResultsArgumentCaptor.capture());
        StudentExamResults capturedStudentExamResults = studentExamResultsArgumentCaptor.getValue();

        assertThat(capturedStudentExamResults.getResult()).isEqualTo(newStudentExamResults.getResult());
        assertThat(capturedStudentExamResults.getStudent()).isEqualTo(newStudentExamResults.getStudent());
        assertThat(capturedStudentExamResults.getSubject()).isEqualTo(newStudentExamResults.getSubject());


    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentExamResultsRepository).deleteById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingStudentExamResults() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(studentExamResultsRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }
}