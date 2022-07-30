package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.StudentForecast;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentForecastConverter implements Converter<StudentForecast, StudentForecastDto>,
    ListConverter<StudentForecast, StudentForecastDto>{

    @Override
    public StudentForecastDto convert(StudentForecast studentForecast) {

        Long id = studentForecast.getId();
        Long admissionId = studentForecast.getAdmission().getId();

        return new StudentForecastDto(id, admissionId);

    }

    @Override
    public List<StudentForecastDto> convert(List<StudentForecast> studentForecasts) {
        List<StudentForecastDto> studentForecastDtoList = new ArrayList<>();
        studentForecasts.
                forEach(studentForecast -> {
                    StudentForecastDto studentForecastDto = convert(studentForecast);
                    studentForecastDtoList.add(studentForecastDto);
                });
        return studentForecastDtoList;
    }
}
