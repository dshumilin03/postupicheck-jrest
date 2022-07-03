package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

@Component
public class ExamResultsReverseConverter implements Converter<StudentExamResultsDto, StudentExamResults> {

    private final SubjectService subjectService;
    private final StudentService studentService;

    public ExamResultsReverseConverter(SubjectService subjectService, StudentService studentService) {
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @Override
    public StudentExamResults convert(StudentExamResultsDto examResultsDto) {

        long subjectId = examResultsDto.getSubjectId();
        Subject subject = subjectService.get(subjectId);

        long studentId = examResultsDto.getStudentId();
        Student student = studentService.get(studentId);

        int result = examResultsDto.getResult();

        return new StudentExamResults(result, student, subject);

    }
}
