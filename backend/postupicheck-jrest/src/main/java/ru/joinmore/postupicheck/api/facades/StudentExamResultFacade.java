package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.StudentExamResultConverter;
import ru.joinmore.postupicheck.api.converters.StudentExamResultReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.services.StudentExamResultService;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.*;

@Component
public class StudentExamResultFacade {

    private final StudentService studentService;
    private final StudentExamResultConverter converter;
    private final StudentExamResultReverseConverter reverseConverter;
    private final StudentExamResultService studentExamResultService;

    public StudentExamResultFacade(
            StudentService studentService,
            StudentExamResultConverter converter,
            StudentExamResultReverseConverter reverseConverter,
            StudentExamResultService studentExamResultService) {
        this.studentService = studentService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
        this.studentExamResultService = studentExamResultService;
    }

    public StudentExamResultDto create(StudentExamResultDto studentExamResultDto) {
        StudentExamResult studentExamResult = reverseConverter.convert(studentExamResultDto);
        StudentExamResult newStudentExamResult = studentExamResultService.create(studentExamResult);

        return converter.convert(newStudentExamResult);

    }

    public List<StudentExamResultDto> getAll() {
        List<StudentExamResult> allStudentExamResults = studentExamResultService.getAll();

        return converter.convert(allStudentExamResults);
    }

    public StudentExamResultDto get(long id) {
        StudentExamResult studentExamResult = studentExamResultService.get(id);

        return converter.convert(studentExamResult);
    }

    public StudentExamResultDto replace(StudentExamResultDto updatedStudentExamResultDto, long id) {
        StudentExamResult updatedStudentExamResult = reverseConverter.convert(updatedStudentExamResultDto);
        StudentExamResult newStudentExamResult = studentExamResultService.replace(updatedStudentExamResult, id);

        return converter.convert(newStudentExamResult);
    }

    public void delete(long id) {
        studentExamResultService.delete(id);
    }


    public List<StudentExamResultDto> getAllStudentResults(Long id) {
        List<StudentExamResult> allStudentResultsByStudentId = studentExamResultService.getAllStudentResultsByStudentId(id);

        return converter.convert(allStudentResultsByStudentId);
    }
}
