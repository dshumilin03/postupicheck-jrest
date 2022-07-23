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
        long newUniversityId = newAdmissionDto.getUniversityId();
        long newCourseId = newAdmissionDto.getCourseId();
        long admissionId = newAdmissionDto.getId();

        Student newStudent = studentService.get(newStudentId);
        University newUniversity = universityService.get(newUniversityId);
        Course newCourse = courseService.get(newCourseId);

        return new Admission(admissionId, newStudent, newUniversity, newCourse);
    }
}
