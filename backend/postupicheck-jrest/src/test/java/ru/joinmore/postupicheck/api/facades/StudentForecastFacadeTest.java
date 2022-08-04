package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.StudentForecastConverter;
import ru.joinmore.postupicheck.api.converters.StudentForecastReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.services.StudentForecastService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentForecastFacadeTest {

    private StudentForecastFacade testInstance;
    @Mock
    private StudentForecastService studentForecastService;
    @Mock
    private StudentForecastConverter converter;
    @Mock
    private StudentForecastReverseConverter reverseConverter;

    @BeforeEach
    void setUp() {
        testInstance = new StudentForecastFacade(studentForecastService, converter, reverseConverter);
    }

    @Test
    void shouldCallStudentForecastServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        StudentForecast studentForecast = mock(StudentForecast.class);
        when(studentForecastService.get(id)).thenReturn(studentForecast);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(studentForecast);

    }

    @Test
    void shouldReturnConvertedStudentForecast_WhenGet() {
        // given
        long id = 5L;
        StudentForecast studentForecast = mock(StudentForecast.class);
        StudentForecastDto convertedStudentForecast = mock(StudentForecastDto.class);
        when(studentForecastService.get(id)).thenReturn(studentForecast);
        when(converter.convert(studentForecast)).thenReturn(convertedStudentForecast);

        // when
        StudentForecastDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedStudentForecast);

    }

    @Test
    void shouldCallConvertListAndStudentForecastService_WhenGetAll() {
        // given
        List<StudentForecast> studentForecastList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(studentForecastService).getAll();
        verify(converter).convert(studentForecastList);

    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<StudentForecast> studentForecastList = new ArrayList<>();
        List<StudentForecastDto> convertedList = new ArrayList<>();
        StudentForecastDto studentForecastDto1 = mock(StudentForecastDto.class);
        StudentForecastDto studentForecastDto2 = mock(StudentForecastDto.class);
        StudentForecastDto studentForecastDto3 = mock(StudentForecastDto.class);
        convertedList.add(studentForecastDto1);
        convertedList.add(studentForecastDto2);
        convertedList.add(studentForecastDto3);
        when(studentForecastService.getAll()).thenReturn(studentForecastList);
        when(converter.convert(studentForecastList)).thenReturn(convertedList);

        // when
        List<StudentForecastDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(studentForecastDto1, studentForecastDto2, studentForecastDto3);

    }

    @Test
    void shouldCallReverseConverterAndStudentForecastServiceAndConverter_WhenCreate() {
        // given
        StudentForecastDto newStudentForecastDto = mock(StudentForecastDto.class);
        StudentForecast newStudentForecast = mock(StudentForecast.class);
        StudentForecast createdStudentForecast = mock(StudentForecast.class);
        when(reverseConverter.convert(newStudentForecastDto)).thenReturn(newStudentForecast);
        when(studentForecastService.create(newStudentForecast)).thenReturn(createdStudentForecast);

        // when
        testInstance.create(newStudentForecastDto);

        // then
        verify(converter).convert(createdStudentForecast);
    }

    @Test
    void shouldReturnConvertedStudentForecast_WhenCreate() {
        // given
        StudentForecastDto newStudentForecastDto = mock(StudentForecastDto.class);
        StudentForecast newStudentForecast = mock(StudentForecast.class);
        StudentForecast createdStudentForecast = mock(StudentForecast.class);
        StudentForecastDto convertedStudentForecast = mock(StudentForecastDto.class);
        when(reverseConverter.convert(newStudentForecastDto)).thenReturn(newStudentForecast);
        when(studentForecastService.create(newStudentForecast)).thenReturn(createdStudentForecast);
        when(converter.convert(createdStudentForecast)).thenReturn(convertedStudentForecast);

        // when
        StudentForecastDto result = testInstance.create(newStudentForecastDto);

        // then
        assertThat(result).isEqualTo(convertedStudentForecast);
    }

    @Test
    void shouldCallReverseConverterAndStudentForecastServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        StudentForecastDto updatedStudentForecastDto = mock(StudentForecastDto.class);
        StudentForecast updatedStudentForecast = mock(StudentForecast.class);
        StudentForecast newStudentForecast = mock(StudentForecast.class);
        when(reverseConverter.convert(updatedStudentForecastDto)).thenReturn(updatedStudentForecast);
        when(studentForecastService.replace(updatedStudentForecast, id)).thenReturn(newStudentForecast);

        // when
        testInstance.replace(updatedStudentForecastDto, id);

        // then
        verify(converter).convert(newStudentForecast);
    }

    @Test
    void shouldReturnConvertedStudentForecast_WhenReplace() {
        // given
        long id = 515L;
        StudentForecastDto updatedStudentForecastDto = mock(StudentForecastDto.class);
        StudentForecast updatedStudentForecast = mock(StudentForecast.class);
        StudentForecast newStudentForecast = mock(StudentForecast.class);
        StudentForecastDto convertedStudentForecast = mock(StudentForecastDto.class);
        when(reverseConverter.convert(updatedStudentForecastDto)).thenReturn(updatedStudentForecast);
        when(studentForecastService.replace(updatedStudentForecast, id)).thenReturn(newStudentForecast);
        when(converter.convert(newStudentForecast)).thenReturn(convertedStudentForecast);

        // when
        StudentForecastDto result = testInstance.replace(updatedStudentForecastDto, id);

        // then
        assertThat(result).isEqualTo(convertedStudentForecast);
    }


    @Test
    void shouldCallStudentForecastServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(studentForecastService).delete(id);
    }
}