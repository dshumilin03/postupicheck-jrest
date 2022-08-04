package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentForecastRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentForecastServiceTest {

    private StudentForecastService testInstance;
    @Mock
    StudentForecastRepository studentForecastRepository;

    @BeforeEach
    void setUp() {
        testInstance = new StudentForecastService(studentForecastRepository);
    }

    @Test
    void shouldCallRepositoryGetAll() {
        // when
        testInstance.getAll();

        // then
        verify(studentForecastRepository).findAll();

    }

    @Test
    void shouldReturnAllForecasts_WhenRepositoryGetAll() {
        // given
        List<StudentForecast> studentForecastList = new ArrayList<>();
        StudentForecast studentForecast1 = mock(StudentForecast.class);
        StudentForecast studentForecast2 = mock(StudentForecast.class);
        StudentForecast studentForecast3 = mock(StudentForecast.class);
        studentForecastList.add(studentForecast1);
        studentForecastList.add(studentForecast2);
        studentForecastList.add(studentForecast3);
        when(studentForecastRepository.findAll()).thenReturn(studentForecastList);

        // when
        List<StudentForecast> result = testInstance.getAll();

        // then
        assertThat(result).isEqualTo(studentForecastList);

    }

    @Test
    void shouldCallRepositoryFindById() {
        // given
        long id = 1L;
        StudentForecast studentForecast = mock(StudentForecast.class);
        when(studentForecastRepository.findById(id)).thenReturn(Optional.of(studentForecast));

        // when
        testInstance.get(id);

        // then
        verify(studentForecastRepository).findById(id);
    }

    @Test
    void shouldReturnForecast_WhenFindById() {
        // given
        long id = 1L;
        StudentForecast studentForecast = mock(StudentForecast.class);
        when(studentForecastRepository.findById(id)).thenReturn(Optional.of(studentForecast));

        // when
        StudentForecast result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(studentForecast);
    }

    @Test
    void shouldThrowResourceNotExists_WhenFindById() {
        // given
        long id = 1L;

        // when
        when(studentForecastRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> testInstance.get(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student forecast with id [1]");
    }

    @Test
    void shouldSaveForecastIfNotExists() {
        // given
        Admission admission = new Admission();
        Student student = mock(Student.class);
        admission.setStudent(student);
        StudentForecast studentForecast = new StudentForecast(admission);
        when(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).thenReturn(false);

        // when
        testInstance.create(studentForecast);

        // then
        verify(studentForecastRepository).save(studentForecast);

    }

    @Test
    void shouldReturnSavedForecast() {
        // given
        Admission admission = new Admission();
        Student student = mock(Student.class);
        admission.setStudent(student);
        StudentForecast studentForecast = new StudentForecast(admission);
        when(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).thenReturn(false);
        when(studentForecastRepository.save(studentForecast)).thenReturn(studentForecast);

        // when
        StudentForecast result = testInstance.create(studentForecast);

        // then
        assertThat(result).isEqualTo(studentForecast);

    }

    @Test
    void shouldNotSaveForecastIfExists() {
        // given
        Admission admission = new Admission();
        Student student = new Student();
        admission.setStudent(student);
        student.setName("testName");
        StudentForecast studentForecast = new StudentForecast(admission);

        // when
        when(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(studentForecast));
        verify(studentForecastRepository, never()).save(studentForecast);

    }

    @Test
    void shouldThrowAlreadyExistsExceptionIfForecastExists() {
        // given
        Admission admission = new Admission();
        Student student = new Student();
        admission.setStudent(student);
        student.setName("testName");
        StudentForecast studentForecast = new StudentForecast(admission);

        // when
        when(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(studentForecast))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("StudentForecast for student testName ");

    }

    @Test
    void shouldReplaceOldForecastByNewForecast() {

        // given
        StudentForecast oldStudentForecast = mock(StudentForecast.class);
        Admission newAdmission = mock(Admission.class);
        StudentForecast newStudentForecast = new StudentForecast(newAdmission);
        long id = 2L;
        when(studentForecastRepository.findById(id)).thenReturn(Optional.of(oldStudentForecast));

        // when
        testInstance.replace(newStudentForecast, id);

        // then
        InOrder inOrder = inOrder(oldStudentForecast, studentForecastRepository);
        inOrder.verify(oldStudentForecast).setAdmission(newAdmission);
        inOrder.verify(studentForecastRepository).save(oldStudentForecast);

    }

    @Test
    void shouldReturnReplacedForecast() {
        // given
        StudentForecast oldStudentForecast = mock(StudentForecast.class);
        Admission newAdmission = mock(Admission.class);
        StudentForecast newStudentForecast = new StudentForecast(newAdmission);
        long id = 2L;
        when(studentForecastRepository.findById(id)).thenReturn(Optional.of(oldStudentForecast));
        when(studentForecastRepository.save(oldStudentForecast)).thenReturn(oldStudentForecast);

        // when
        StudentForecast result = testInstance.replace(newStudentForecast, id);

        // then
        assertThat(result).isEqualTo(oldStudentForecast);

    }

    @Test
    void shouldNotReplaceForecastIfNotExists() {
        // given
        StudentForecast oldStudentForecast = mock(StudentForecast.class);
        long id = 2;
        when(studentForecastRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new StudentForecast(), id));

        // then
        verify(studentForecastRepository, never()).save(oldStudentForecast);

    }

    @Test
    void shouldThrowResourceIsNotExistsException_WhenReplace() {
        // given
        long id = 2L;

        // when
        when(studentForecastRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new StudentForecast(), id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student forecast with id [2]");

    }


    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 4L;

        // when
        testInstance.delete(id);

        // then
        verify(studentForecastRepository).deleteById(id);

    }

    @Test
    void shouldThrowResourceNotExitsException_WhenDelete() {
        // given
        long id = 13L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(studentForecastRepository).deleteById(id);

        // then
        assertThatThrownBy(() -> testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student forecast with id [13]");
    }

    @Test
    void shouldReturnStudentForecast_WhenFindStudentForecastByAdmissionStudentId() {
        // given
        long id = 1;
        StudentForecast studentForecast = mock(StudentForecast.class);
        when(studentForecastRepository.findStudentForecastByAdmissionStudentId(id)).thenReturn(studentForecast);

        // when
        StudentForecast result = testInstance.getStudentForecast(id);

        // then
        assertThat(result).isEqualTo(studentForecast);

    }

    @Test
    void shouldThrowResourceNotExistsException_WhenGetStudentForecastByAdmissionStudentId() {
        // given
        long id = 134L;

        // when
        when(studentForecastRepository.findStudentForecastByAdmissionStudentId(id)).thenReturn(null);

        // then
        assertThatThrownBy(() -> testInstance.getStudentForecast(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student forecast with id [134]");

    }

}