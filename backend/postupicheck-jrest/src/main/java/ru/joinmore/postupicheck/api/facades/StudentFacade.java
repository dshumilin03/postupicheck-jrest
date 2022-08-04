package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.AdmissionConverter;
import ru.joinmore.postupicheck.api.converters.StudentConverter;
import ru.joinmore.postupicheck.api.converters.StudentReverseConverter;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.services.AdmissionService;
import ru.joinmore.postupicheck.api.services.StudentAdmissionService;
import ru.joinmore.postupicheck.api.services.StudentForecastService;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.List;

@Component
public class StudentFacade {

    private final StudentService studentService;
    private final StudentConverter converter;
    private final StudentReverseConverter reverseConverter;

    private final StudentAdmissionService studentAdmissionService;

    private final AdmissionConverter admissionConverter;
    private final StudentForecastService studentForecastService;

    public StudentFacade(StudentService studentService,
                         StudentForecastService studentForecastService,
                         StudentAdmissionService studentAdmissionService,
                         StudentConverter converter,
                         StudentReverseConverter reverseConverter,
                         AdmissionConverter admissionConverter) {
        this.studentService = studentService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
        this.studentAdmissionService = studentAdmissionService;
        this.admissionConverter = admissionConverter;
        this.studentForecastService = studentForecastService;
    }


    public StudentDto get(long id) {

        Student student =  studentService.get(id);

        return converter.convert(student);
    }

    public List<StudentDto> getAll() {

        List<Student> allStudents = studentService.getAll();

        return converter.convert(allStudents);
    }

    public StudentDto create(StudentDto newStudentDto) {

        Student newStudent = reverseConverter.convert(newStudentDto);
        Student createdStudent = studentService.create(newStudent);

        return converter.convert(createdStudent);
    }

    public StudentDto replace(StudentDto updatedStudentDto, long id) {

        Student updatedStudent = reverseConverter.convert(updatedStudentDto);
        Student newStudent = studentService.replace(updatedStudent, id);

        return converter.convert(newStudent);
    }

    public void delete(long id) {
        studentService.delete(id);
    }

    public List<AdmissionDto> getStudentAdmissions(long id) {
        List<Admission> admissionsByStudent = studentAdmissionService.getStudentAdmissions(id);

        return admissionConverter.convert(admissionsByStudent);
    }

    public AdmissionDto getStudentConsentAdmission(long id) {
        Admission admission = studentAdmissionService.getStudentConsentAdmission(id);
        return admissionConverter.convert(admission);
    }

    public List<AdmissionDto> getStudentAvailableAdmissions(Long id) {
        List<Admission> availableAdmissions = studentAdmissionService.getStudentAvailableAdmissions(id);

        return admissionConverter.convert(availableAdmissions);
    }

    public AdmissionDto getStudentForecastAdmission(Long id) {
            Admission forecast = studentForecastService.getStudentForecast(id).getAdmission();

            return admissionConverter.convert(forecast);
    }
}
