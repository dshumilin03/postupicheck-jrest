package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentExamResultConverter implements
        Converter<StudentExamResult, StudentExamResultDto>,
        ListConverter<StudentExamResult, StudentExamResultDto> {

    @Override
    public StudentExamResultDto convert(StudentExamResult result) {
        int points = result.getPoints();
        long studentId = result
                        .getStudent()
                        .getId();
        long subjectId = result
                        .getSubject()
                        .getId();
        long resultId = result.getId();

        return new StudentExamResultDto(resultId, studentId, subjectId, points);
    }

    @Override
    public List<StudentExamResultDto> convert(List<StudentExamResult> studentExamResults) {
        List<StudentExamResultDto> studentExamResultDtoList = new ArrayList<>();

        studentExamResults.
                forEach(studentExamResult -> {
                    StudentExamResultDto studentExamResultDto = convert(studentExamResult);
                    studentExamResultDtoList.add(studentExamResultDto);
                });

        return studentExamResultDtoList;
    }
}
