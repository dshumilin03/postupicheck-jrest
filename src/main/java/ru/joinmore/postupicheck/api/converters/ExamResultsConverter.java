package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;

@Component
public class ExamResultsConverter implements Converter<StudentExamResults, StudentExamResultsDto> {

    @Override
    public StudentExamResultsDto convert(StudentExamResults result) {

        StudentExamResultsDto studentExamResultsDto = new StudentExamResultsDto();
        studentExamResultsDto.setResult(result.getResult());
        studentExamResultsDto.setStudentId(result.getSubject().getId());
        studentExamResultsDto.setSubjectId(result.getStudent().getId());
        studentExamResultsDto.setId(result.getId());

        return studentExamResultsDto;
    }
}
