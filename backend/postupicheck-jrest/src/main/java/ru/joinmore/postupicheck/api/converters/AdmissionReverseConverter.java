package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.StudentService;

@Component
public class AdmissionReverseConverter implements Converter<AdmissionDto, Admission> {

    private final StudentService studentService;
    private final CourseService courseService;

    public AdmissionReverseConverter(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public Admission convert(AdmissionDto newAdmissionDto) {

        long newStudentId = newAdmissionDto.getStudentId();
        long newCourseId = newAdmissionDto.getCourseId();
        long admissionId = newAdmissionDto.getId();
        boolean consent = newAdmissionDto.isConsent();
        int points = newAdmissionDto.getPoints();

        Student newStudent = studentService.get(newStudentId);
        Course newCourse = courseService.get(newCourseId);

        return new Admission(admissionId, newStudent, newCourse, consent, points);
    }
}
