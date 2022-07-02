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

        AdmissionDto admissionDto = new AdmissionDto();
        admissionDto.setId(admissionId);
        admissionDto.setStudentId(studentId);
        admissionDto.setUniversityId(universityId);
        admissionDto.setCourseId(courseId);

        return admissionDto;
    }
}
