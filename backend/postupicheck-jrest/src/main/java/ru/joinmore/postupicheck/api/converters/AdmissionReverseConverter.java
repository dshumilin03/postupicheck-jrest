package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.Optional;

@Component
public class AdmissionReverseConverter implements Converter<AdmissionDto, Admission> {

    private final StudentService studentService;
    private final UniversityService universityService;
    private final CourseService courseService;

    public AdmissionReverseConverter(StudentService studentService, UniversityService universityService, CourseService courseService) {
        this.studentService = studentService;
        this.universityService = universityService;
        this.courseService = courseService;
    }

    @Override
    public Admission convert(AdmissionDto newAdmissionDto) {

        long newStudentId = newAdmissionDto.getStudentId();
        long newCourseId = newAdmissionDto.getCourseId();
        long admissionId = newAdmissionDto.getId();
        boolean approval = newAdmissionDto.isApproval();

        Student newStudent = studentService.get(newStudentId);
        Course newCourse = courseService.get(newCourseId);

        return new Admission(admissionId, newStudent, newCourse, approval);
    }
}
