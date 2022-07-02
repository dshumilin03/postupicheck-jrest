package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.AdmissionConverter;
import ru.joinmore.postupicheck.api.converters.AdmissionReverseConverter;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.AdmissionService;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdmissionFacade {

    private final AdmissionService admissionService;
    private final StudentService studentService;
    private final UniversityService universityService;
    private final CourseService courseService;

    private final AdmissionConverter converter;

    private final AdmissionReverseConverter reverseConverter;

    public AdmissionFacade(AdmissionService admissionService,
                           StudentService studentService,
                           UniversityService universityService,
                           CourseService courseService,
                           AdmissionConverter converter,
                           AdmissionReverseConverter reverseConverter) {
        this.admissionService = admissionService;
        this.studentService = studentService;
        this.universityService = universityService;
        this.courseService = courseService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;

    }

    public AdmissionDto get(long id) {

        Admission admission = admissionService.get(id);

        return converter.convert(admission);
    }

    public List<AdmissionDto> getAll() {

        List<Admission> admissionList = admissionService.getAll();
        List<AdmissionDto> admissionDtoList = new ArrayList<>();

        admissionList.
                forEach(admission -> {
                    AdmissionDto admissionDto = converter.convert(admission);
                    admissionDtoList.add(admissionDto);
                });

        return admissionDtoList;
    }

    public AdmissionDto create(AdmissionDto newAdmissionDto) {

        Admission newAdmission = reverseConverter.convert(newAdmissionDto);
        Admission createdAdmission = admissionService.create(newAdmission);

        return converter.convert(createdAdmission);
    }

    public AdmissionDto replace(AdmissionDto updatedAdmissionDto, long id) {

        Admission updatedAdmission = reverseConverter.convert(updatedAdmissionDto);
        Admission newAdmission = admissionService.replace(updatedAdmission, id);

        return converter.convert(newAdmission);
    }

    public void delete(long id) {
        admissionService.delete(id);
    }

}