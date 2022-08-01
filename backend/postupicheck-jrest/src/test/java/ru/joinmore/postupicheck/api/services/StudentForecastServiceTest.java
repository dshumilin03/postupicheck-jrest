package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentForecastRepository;

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

    private StudentForecastService underTest;
    @Mock
    StudentForecastRepository studentForecastRepository;

    @BeforeEach
    void setUp() {
        underTest = new StudentForecastService(studentForecastRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(studentForecastRepository).findAll();
    }

    @Test
    void get() {
        //given
        long id = 1;
        StudentForecast studentForecast = new StudentForecast();
        given(studentForecastRepository.findById(id)).willReturn(Optional.of(studentForecast));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentForecastRepository).findById(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(1);
    }

    @Test
    void create() {
        //given
        StudentForecast studentForecast = new StudentForecast();
        Admission admission = new Admission();
        studentForecast.setAdmission(admission);
        Student student = new Student();
        admission.setStudent(student);
        given(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).willReturn(false);
        //when
        underTest.create(studentForecast);
        //then
        ArgumentCaptor<StudentForecast> studentForecastArgumentCaptor = ArgumentCaptor.forClass(StudentForecast.class);

        verify(studentForecastRepository).save(studentForecastArgumentCaptor.capture());

        StudentForecast capturedStudentForecast = studentForecastArgumentCaptor.getValue();

        assertThat(capturedStudentForecast).isEqualTo(studentForecast);
    }

    @Test
    void createExistedForecast() {
        //given
        StudentForecast studentForecast = new StudentForecast();
        Admission admission = new Admission();
        studentForecast.setAdmission(admission);
        Student student = new Student();
        admission.setStudent(student);
        //when
        given(studentForecastRepository.existsStudentForecastByAdmissionStudent(student)).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(studentForecast))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("StudentForecast");

        verify(studentForecastRepository, never()).save(any());
    }

    @Test
    void replace() {

        //given
        Admission oldAdmission = new Admission();
        StudentForecast oldStudentForecast = new StudentForecast(oldAdmission);
        Admission newAdmission = new Admission();
        StudentForecast newStudentForecast = new StudentForecast(newAdmission);
        long id = 2;
        given(studentForecastRepository.findById(id)).willReturn(Optional.of(oldStudentForecast));
        //when
        underTest.replace(newStudentForecast, id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentForecastRepository).findById(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(2);
        ArgumentCaptor<StudentForecast> studentForecastArgumentCaptor = ArgumentCaptor.forClass(StudentForecast.class);
        verify(studentForecastRepository).save(studentForecastArgumentCaptor.capture());
        StudentForecast capturedStudentForecast = studentForecastArgumentCaptor.getValue();

        assertThat(capturedStudentForecast.getAdmission()).isEqualTo(newAdmission);
    }

    @Test
    void delete() {
        //given
        long id = 4;
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentForecastRepository).deleteById(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(4);
    }

    @Test
    void deleteNotExistingForecast() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(studentForecastRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");
    }

    @Test
    void getStudentForecast() {
        //given
        StudentForecast forecast = new StudentForecast();
        long id = 1;
        given(studentForecastRepository.findStudentForecastByAdmissionStudentId(id)).willReturn(forecast);
        //when
        underTest.getStudentForecast(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentForecastRepository).findStudentForecastByAdmissionStudentId(longArgumentCaptor.capture());
        long capturedLong = longArgumentCaptor.getValue();
        assertThat(capturedLong).isEqualTo(1);
    }

    @Test
    void getNotExistingStudentForecast() {
        //given
        long id = 1;
        given(studentForecastRepository.findStudentForecastByAdmissionStudentId(id)).willReturn(null);
        //then
        assertThatThrownBy(() -> underTest.getStudentForecast(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");
    }
}