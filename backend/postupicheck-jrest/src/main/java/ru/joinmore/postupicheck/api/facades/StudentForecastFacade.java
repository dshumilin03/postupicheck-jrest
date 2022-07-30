package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.StudentForecastConverter;
import ru.joinmore.postupicheck.api.converters.StudentForecastReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.services.StudentForecastService;

import java.util.List;

@Component
public class StudentForecastFacade {

    private final StudentForecastService studentForecastService;
    private final StudentForecastConverter converter;
    private final StudentForecastReverseConverter reverseConverter;

    public StudentForecastFacade(StudentForecastService studentForecastService,
                                 StudentForecastConverter converter,
                                 StudentForecastReverseConverter reverseConverter) {
        this.studentForecastService = studentForecastService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    public StudentForecastDto get(long id) {

        StudentForecast studentForecast =  studentForecastService.get(id);

        return converter.convert(studentForecast);
    }

    public List<StudentForecastDto> getAll() {

        List<StudentForecast> allStudentForecasts = studentForecastService.getAll();

        return converter.convert(allStudentForecasts);
    }

    public StudentForecastDto create(StudentForecastDto newStudentForecastDto) {

        StudentForecast newStudentForecast = reverseConverter.convert(newStudentForecastDto);
        StudentForecast createdStudentForecast = studentForecastService.create(newStudentForecast);

        return converter.convert(createdStudentForecast);
    }

    public StudentForecastDto replace(StudentForecastDto updatedStudentForecastDto, long id) {

        StudentForecast updatedStudentForecast = reverseConverter.convert(updatedStudentForecastDto);
        StudentForecast newStudentForecast = studentForecastService.replace(updatedStudentForecast, id);

        return converter.convert(newStudentForecast);
    }

    public void delete(long id) {
        studentForecastService.delete(id);
    }

}
