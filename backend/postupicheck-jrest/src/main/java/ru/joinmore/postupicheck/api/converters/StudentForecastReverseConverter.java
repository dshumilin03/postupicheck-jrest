package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.services.AdmissionService;

@Component
public class StudentForecastReverseConverter implements Converter<StudentForecastDto, StudentForecast> {

    private final AdmissionService admissionService;

    public StudentForecastReverseConverter(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @Override
    public StudentForecast convert(StudentForecastDto studentForecastDto) {

        long admissionId = studentForecastDto.getAdmissionId();
        Admission admission =  admissionService.get(admissionId);
        long studentForecastId = studentForecastDto.getId();

        return new StudentForecast(studentForecastId, admission);
    }
}
