package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentExamResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicIntegerFieldUpdater;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentExamResultsResultServiceTest {

    @Mock
    private StudentExamResultRepository studentExamResultRepository;
    private StudentExamResultService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentExamResultService(studentExamResultRepository);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(studentExamResultRepository).findAll();

    }

    @Test
    void shouldReturnAllResults_WhenRepositoryFindAll() {
        // given
        List<StudentExamResult> studentExamResultList = new ArrayList<>();
        StudentExamResult studentExamResult1 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult2 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult3 = mock(StudentExamResult.class);
        studentExamResultList.add(studentExamResult1);
        studentExamResultList.add(studentExamResult2);
        studentExamResultList.add(studentExamResult3);
        when(studentExamResultRepository.findAll()).thenReturn(studentExamResultList);

        // when
        List<StudentExamResult> result = testInstance.getAll();

        // then
        assertThat(result).contains(studentExamResult1, studentExamResult2, studentExamResult3);

    }

    @Test
    void shouldCallRepositoryFindById() {
        // given
        long id = 345L;
        StudentExamResult studentExamResult = mock(StudentExamResult.class);
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.of(studentExamResult));

        // when
        testInstance.get(id);

        // then
        verify(studentExamResultRepository).findById(id);
    }

    @Test
    void shouldReturnResult_WhenFindById() {
        // given
        long id = 345L;
        StudentExamResult studentExamResult = mock(StudentExamResult.class);
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.of(studentExamResult));

        // when
        StudentExamResult result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(studentExamResult);
    }

    @Test
    void shouldThrowResourceNotExists_WhenFindByIdDoesntExists() {
        // given
        long id = 345L;

        // when
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> testInstance.get(id)).
                isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student exam result with id [345]");
    }

    @Test
    void shouldSaveResultIfNotExists() {
        // given
        StudentExamResult studentExamResult = mock(StudentExamResult.class);

        // when
        testInstance.create(studentExamResult);

        // then
        verify(studentExamResultRepository).save(studentExamResult);
    }

    @Test
    void shouldReturnCreatedResult() {
        // given
        StudentExamResult studentExamResult = mock(StudentExamResult.class);
        when(studentExamResultRepository.save(studentExamResult)).thenReturn(studentExamResult);

        // when
        StudentExamResult result = testInstance.create(studentExamResult);

        // then
        assertThat(result).isEqualTo(studentExamResult);

    }

    @Test
    void shouldNotSaveResultIfExists() {
        // given
        Student student = mock(Student.class);
        Subject subject = mock(Subject.class);
        StudentExamResult studentExamResult = mock(StudentExamResult.class);

        // when
        when(studentExamResultRepository.existsBySubjectAndStudent(subject, student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(studentExamResult));
        verify(studentExamResultRepository, never()).save(studentExamResult);
    }
    @Test
    void shouldThrowAlreadyExistsExceptionIfExists() {
        // given
        Student student = mock(Student.class);
        Subject subject = new Subject();
        subject.setName("testName");
        StudentExamResult studentExamResult = new StudentExamResult();
        studentExamResult.setStudent(student);
        studentExamResult.setSubject(subject);

        // when
        when(studentExamResultRepository.existsBySubjectAndStudent(subject, student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(studentExamResult))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(studentExamResult.getSubject().getName());

    }

    @Test
    void shouldReplaceOldResultByNewResult() {
        // given
        int newResult = 80;
        Student newStudent = mock(Student.class);
        Subject newSubject = mock(Subject.class);
        StudentExamResult oldStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult newStudentExamResult = new StudentExamResult(newResult, newStudent, newSubject);
        long id = 435L;
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.of(oldStudentExamResult));

        // when
        testInstance.replace(newStudentExamResult, id);

        // then
        InOrder inOrder = inOrder(oldStudentExamResult, studentExamResultRepository);
        inOrder.verify(oldStudentExamResult).setStudent(newStudent);
        inOrder.verify(oldStudentExamResult).setSubject(newSubject);
        inOrder.verify(oldStudentExamResult).setPoints(newResult);
        inOrder.verify(studentExamResultRepository).save(oldStudentExamResult);

    }

    @Test
    void shouldReturnReplacedResult() {
        // given
        int newResult = 80;
        Student newStudent = mock(Student.class);
        Subject newSubject = mock(Subject.class);

        StudentExamResult oldStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult newStudentExamResult = new StudentExamResult(newResult, newStudent, newSubject);
        long id = 435L;
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.of(oldStudentExamResult));
        when(studentExamResultRepository.save(oldStudentExamResult)).thenReturn(oldStudentExamResult);

        // when
        StudentExamResult result = testInstance.replace(newStudentExamResult, id);

        // then
        assertThat(result).isEqualTo(oldStudentExamResult);

    }

    @Test
    void shouldNotReplaceResult_WhenDoesntExist() {
        // given
        StudentExamResult oldStudentExamResult = mock(StudentExamResult.class);
        long id = 435L;
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new StudentExamResult(), id));

        // then
        verify(studentExamResultRepository, never()).save(oldStudentExamResult);

    }

    @Test
    void shouldThrowResourceNotExistsException_WhenReplace() {
        // given
        StudentExamResult oldStudentExamResult = mock(StudentExamResult.class);
        long id = 435L;

        // when
        when(studentExamResultRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> testInstance.replace(new StudentExamResult(), id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student exam result with id [435]");

    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 234L;

        // when
        testInstance.delete(id);

        // then
        verify(studentExamResultRepository).deleteById(id);

    }

    @Test
    void shouldThrowRecourseNotExistsException_WhenDelete() {
        // given
        long id = 45L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(studentExamResultRepository).deleteById(id);

        // then
        assertThatThrownBy(() -> testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student exam result with id [45]");

    }

    @Test
    void shouldCallRepositoryAllStudentResults() {
        // given
        Student student = mock(Student.class);

        // when
        testInstance.getAllStudentResults(student);

        // then
        verify(studentExamResultRepository).findStudentExamResultsByStudent(student);

    }

    @Test
    void shouldReturnStudentResults_WhenGetAllStudentResults() {
        // given
        Student student = mock(Student.class);
        List<StudentExamResult> studentExamResults = new ArrayList<>();
        StudentExamResult studentExamResult1 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult2 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult3 = mock(StudentExamResult.class);
        studentExamResults.add(studentExamResult1);
        studentExamResults.add(studentExamResult2);
        studentExamResults.add(studentExamResult3);
        when(studentExamResultRepository.findStudentExamResultsByStudent(student)).thenReturn(studentExamResults);

        // when
        List<StudentExamResult> result = testInstance.getAllStudentResults(student);

        // then
        assertThat(result).isEqualTo(studentExamResults);

    }

    @Test
    void shouldCallRepositoryGetPointsByStudentAndSubject() {
        // given
        Student student = mock(Student.class);
        Subject subject = mock(Subject.class);

        // when
        testInstance.getPointsByStudentAndSubject(student, subject);

        // then
        verify(studentExamResultRepository).getPointsByStudentAndSubject(student, subject);

    }

    @Test
    void shouldReturnPoints_WhenGetPointsByStudentAndSubject() {
        // given
        Student student = mock(Student.class);
        Subject subject = mock(Subject.class);
        int points = 135;
        when(studentExamResultRepository.getPointsByStudentAndSubject(student, subject)).thenReturn(points);

        // when
        int result = testInstance.getPointsByStudentAndSubject(student, subject);

        // then
        assertThat(result).isEqualTo(points);

    }

    @Test
    void shouldCallRepositoryFindAllStudentResultsByStudentId() {
        // given
        long id = 5;

        // when
        testInstance.getAllStudentResultsByStudentId(id);

        // then
        verify(studentExamResultRepository).findStudentExamResultsByStudentId(id);

    }

    @Test
    void shouldReturnResults_WhenFindAllStudentResultsByStudentId() {
        // given
        long id = 5;
        List<StudentExamResult> studentExamResults = new ArrayList<>();
        StudentExamResult studentExamResult1 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult2 = mock(StudentExamResult.class);
        StudentExamResult studentExamResult3 = mock(StudentExamResult.class);
        studentExamResults.add(studentExamResult1);
        studentExamResults.add(studentExamResult2);
        studentExamResults.add(studentExamResult3);
        when(studentExamResultRepository.findStudentExamResultsByStudentId(id)).thenReturn(studentExamResults);

        // when
        List<StudentExamResult> result = testInstance.getAllStudentResultsByStudentId(id);

        // then
        assertThat(result).isEqualTo(studentExamResults);

    }
}