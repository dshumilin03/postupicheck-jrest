package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.StudentForecast;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentForecastConverterTest {

    private StudentForecastConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentForecastConverter();
    }

    @Test
    void convert() {
        //given
        Admission admission = new Admission();
        admission.setId(12L);
        StudentForecast studentForecast = new StudentForecast();
        studentForecast.setId(1L);
        studentForecast.setAdmission(admission);
        //when
        StudentForecastDto forecastDto = testInstance.convert(studentForecast);
        //then
        assertThat(forecastDto.getId()).isEqualTo(1L);
        assertThat(forecastDto.getAdmissionId()).isEqualTo(12L);
    }

    @Test
    void convertALl() {
        //given
        List<StudentForecast> studentsForecasts = new ArrayList<>();
        Admission admission = new Admission();
        admission.setId(12L);
        StudentForecast studentForecast = new StudentForecast();
        studentForecast.setId(1L);
        studentForecast.setAdmission(admission);
        Admission admission2 = new Admission();
        admission2.setId(11L);
        StudentForecast studentForecast2 = new StudentForecast();
        studentForecast2.setId(2L);
        studentForecast2.setAdmission(admission2);

        studentsForecasts.add(studentForecast);
        studentsForecasts.add(studentForecast2);
        //when
        List<StudentForecastDto> forecastDtoList = testInstance.convert(studentsForecasts);
        //then
        StudentForecastDto forecastDto1 = forecastDtoList.get(0);
        StudentForecastDto forecastDto2 = forecastDtoList.get(1);
        assertThat(forecastDto1.getId()).isEqualTo(1L);
        assertThat(forecastDto1.getAdmissionId()).isEqualTo(12L);

        assertThat(forecastDto2.getId()).isEqualTo(2L);
        assertThat(forecastDto2.getAdmissionId()).isEqualTo(11L);
    }
}