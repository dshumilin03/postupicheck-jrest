package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentAdmissionService {

    private final StudentService studentService;
    private final AdmissionService admissionService;
    private final StudentExamResultService studentExamResultService;
    private final CourseService courseService;

    public StudentAdmissionService(
            StudentService studentService,
            CourseService courseService,
            StudentExamResultService studentExamResultService,
            AdmissionService admissionService) {
        this.studentService = studentService;
        this.admissionService = admissionService;
        this.studentExamResultService = studentExamResultService;
        this.courseService = courseService;
    }

    public List<Admission> getStudentAdmissions(Long id) {
        return admissionService.findAdmissionsByStudentId(id);
    }

    public Admission getStudentConsentAdmission(Long id) {
        List<Admission> studentAdmissionList = admissionService.findAdmissionsByStudentId(id);
        // TODO make exception if student have no consent yet
        return studentAdmissionList
                .stream()
                .filter(Admission::isConsent)
                .toList()
                .get(0);
    }

    public List<Admission> getStudentAvailableAdmissions(Long id) {
        Student student = studentService.get(id);
        List<Admission> allStudentAdmissions = admissionService.findAdmissionsByStudentId(id);

        return allStudentAdmissions
                .stream()
                .filter(admission -> admission
                        .getCourse()
                        .getCurPassingPoints()
                        <
                        getStudentAdmissionPoints(student, admission.getCourse())
                )
                .toList();
    }

    public int getStudentAdmissionPoints(Student student, Course course) {
        List<Subject> requiredSubjects = course.getRequiredSubjects();
        AtomicInteger result = new AtomicInteger();

        requiredSubjects.
                forEach(subject -> result
                        .addAndGet(studentExamResultService
                                        .getPointsByStudentAndSubject(student, subject)
                        )
                );

        return result.get();
    }
}

