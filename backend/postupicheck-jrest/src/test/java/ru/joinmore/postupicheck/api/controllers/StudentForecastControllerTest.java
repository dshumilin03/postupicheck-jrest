package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.facades.StudentForecastFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentForecastControllerTest {

    private StudentForecastController testInstance;
    @Mock
    StudentForecastFacade studentForecastFacade;

    @BeforeEach
    void setUp() {testInstance = new StudentForecastController(studentForecastFacade);}

    @Test
    void shouldReturnAllStudentsForecastDto() {
        // given
        List<StudentForecastDto> studentForecastDtoList = createStudentForecastDtoList();
        StudentForecastDto studentForecastDto1 = studentForecastDtoList.get(0);
        StudentForecastDto studentForecastDto2 = studentForecastDtoList.get(1);

        when(studentForecastFacade.getAll()).thenReturn(studentForecastDtoList);

        // when
        List<StudentForecastDto> result = testInstance.getAll();

        // then
        assertThat(result).contains(studentForecastDto1, studentForecastDto2);
    }

    @Test
    void shouldReturnCreatedForecastDto() {
        // given
        StudentForecastDto studentForecastDto = mock(StudentForecastDto.class);

        when(studentForecastFacade.create(studentForecastDto)).thenReturn(studentForecastDto);

        // when
        StudentForecastDto result = testInstance.createStudentForecast(studentForecastDto);

        // then
        assertThat(result).isEqualTo(studentForecastDto);
    }

    @Test
    void shouldReturnStudentForecast() {
        // given
        long studentForecastId = 214L;
        StudentForecastDto studentForecastDto = mock(StudentForecastDto.class);

        when(studentForecastFacade.get(studentForecastId)).thenReturn(studentForecastDto);

        // when
        StudentForecastDto result = testInstance.getStudentForecast(studentForecastId);

        // then
        assertThat(result).isEqualTo(studentForecastDto);
    }

    @Test
    void shouldReturnReplacedStudentForecast() {
        // given
        long studentForecastId = 124L;
        StudentForecastDto updatedStudentForecastDto = mock(StudentForecastDto.class);

        when(studentForecastFacade.replace(updatedStudentForecastDto, studentForecastId))
                .thenReturn(updatedStudentForecastDto);

        // when
        StudentForecastDto result = testInstance.replaceStudentForecast(updatedStudentForecastDto, studentForecastId);

        // then
        assertThat(result).isEqualTo(updatedStudentForecastDto);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long studentForecastId = 124L;

        // when
        testInstance.deleteStudentForecast(studentForecastId);

        // then
        verify(studentForecastFacade).delete(studentForecastId);
    }

    private List<StudentForecastDto> createStudentForecastDtoList() {
        StudentForecastDto studentForecastDto1 = mock(StudentForecastDto.class);
        StudentForecastDto studentForecastDto2 = mock(StudentForecastDto.class);

        List<StudentForecastDto> studentForecastDtoList = new ArrayList<>();
        studentForecastDtoList.add(studentForecastDto1);
        studentForecastDtoList.add(studentForecastDto2);

        return studentForecastDtoList;
    }
}