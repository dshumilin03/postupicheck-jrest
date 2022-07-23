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

        long subjectId = studentExamResultDto.getSubjectId();
        Subject subject = subjectService.get(subjectId);

        long studentId = studentExamResultDto.getStudentId();
        Student student = studentService.get(studentId);

        int result = studentExamResultDto.getPoints();
        long studentExamResultId = studentExamResultDto.getId();

        return new StudentExamResult(studentExamResultId, result, student, subject);

    }
}
