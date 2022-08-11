package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

@Component
public class StudentExamResultReverseConverter implements Converter<StudentExamResultDto, StudentExamResult> {

    private final SubjectService subjectService;
    private final StudentService studentService;

    public StudentExamResultReverseConverter(SubjectService subjectService, StudentService studentService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @Override
    public StudentExamResult convert(StudentExamResultDto studentExamResultDto) {
        long studentExamResultId = studentExamResultDto.getId();
        long studentId = studentExamResultDto.getStudentId();
        long subjectId = studentExamResultDto.getSubjectId();
        int result = studentExamResultDto.getPoints();

        Subject subject = subjectService.get(subjectId);
        Student student = studentService.get(studentId);

        return new StudentExamResult(studentExamResultId, result, student, subject);
    }
}
