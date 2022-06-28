package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
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

    public AdmissionFacade(AdmissionService admissionService,
                           StudentService studentService,
                           UniversityService universityService,
                           CourseService courseService) {
        this.admissionService = admissionService;
        this.studentService = studentService;
        this.universityService = universityService;
        this.courseService = courseService;
    }

    public AdmissionDto get(long id) {

        Admission admission = admissionService.get(id);

        return setAdmissionDto(admission);
    }

    public List<AdmissionDto> getAll() {

        List<Admission> admissionList = admissionService.getAll();
        List<AdmissionDto> admissionDtoList = new ArrayList<>();

        for (Admission admission : admissionList) {

            AdmissionDto admissionDto = setAdmissionDto(admission);
            admissionDtoList.add(admissionDto);
        }

        return admissionDtoList;
    }

    public AdmissionDto create(AdmissionDto newAdmissionDto) {

        Admission newAdmission = setAdmission(newAdmissionDto);
        Admission createdAdmission = admissionService.create(newAdmission);

        newAdmissionDto.setId(createdAdmission.getId());

        return newAdmissionDto;
    }

    public AdmissionDto replace(AdmissionDto updatedAdmissionDto, long id) {

        Admission updatedAdmission = setAdmission(updatedAdmissionDto);
        Admission newAdmission = admissionService.replace(updatedAdmission, id);

        updatedAdmissionDto.setId(newAdmission.getId());

        return updatedAdmissionDto;
    }

    public void delete(long id) {
        admissionService.delete(id);
    }

    private Admission setAdmission(AdmissionDto newAdmissionDto) {
        long newStudentId = newAdmissionDto.getStudentId();
        long newUniversityId = newAdmissionDto.getUniversityId();
        long newCourseId = newAdmissionDto.getCourseId();

        Student newStudent = studentService.get(newStudentId);
        University newUniversity = universityService.get(newUniversityId);
        Course newCourse = courseService.get(newCourseId);

        return new Admission(newStudent, newUniversity, newCourse);
    }

    private AdmissionDto setAdmissionDto(Admission admission) {

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
