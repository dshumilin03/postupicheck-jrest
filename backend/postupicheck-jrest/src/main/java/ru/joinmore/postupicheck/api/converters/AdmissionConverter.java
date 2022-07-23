package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;

@Component
public class AdmissionConverter implements Converter<Admission, AdmissionDto> {

    @Override
    public AdmissionDto convert(Admission admission) {

        long admissionId = admission.getId();
        long studentId = admission.getStudent().getId();
        long universityId = admission.getUniversity().getId();
        long courseId = admission.getCourse().getId();

        return new AdmissionDto(admissionId, studentId, universityId, courseId);
    }
}
