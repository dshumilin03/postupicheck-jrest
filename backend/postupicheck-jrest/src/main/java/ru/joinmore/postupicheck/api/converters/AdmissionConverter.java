package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdmissionConverter implements Converter<Admission, AdmissionDto>, ListConverter<Admission, AdmissionDto> {

    @Override
    public AdmissionDto convert(Admission admission) {

        long admissionId = admission.getId();
        long studentId = admission.getStudent().getId();
        long courseId = admission.getCourse().getId();
        boolean consent = admission.isConsent();
        int points = admission.getPoints();

        return new AdmissionDto(admissionId, studentId, courseId, consent, points);
    }

    @Override
    public List<AdmissionDto> convert(List<Admission> admissions) {
        List<AdmissionDto> admissionDtoList = new ArrayList<>();
        admissions.
                forEach(admission -> {
                    AdmissionDto admissionDto = convert(admission);
                    admissionDtoList.add(admissionDto);
                });
        return admissionDtoList;
    }

}
