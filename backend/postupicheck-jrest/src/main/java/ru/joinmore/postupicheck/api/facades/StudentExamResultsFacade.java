package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.ExamResultsConverter;
import ru.joinmore.postupicheck.api.converters.ExamResultsReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.services.StudentExamResultsService;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentExamResultsFacade {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final ExamResultsConverter converter;
    private final ExamResultsReverseConverter reverseConverter;
    private final StudentExamResultsService studentExamResultsService;

    public StudentExamResultsFacade(StudentService studentService,
                                    SubjectService subjectService,
                                    ExamResultsConverter converter,
                                    ExamResultsReverseConverter reverseConverter,
                                    StudentExamResultsService studentExamResultsService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
        this.studentExamResultsService = studentExamResultsService;
    }

    public StudentExamResultsDto create(StudentExamResultsDto examResultsDto) {

        StudentExamResults studentExamResults = reverseConverter.convert(examResultsDto);
        StudentExamResults newExamResults = studentExamResultsService.create(studentExamResults);

        return converter.convert(newExamResults);

    }

    public List<StudentExamResultsDto> getAll() {

        List<StudentExamResults> studentExamResultsList = studentExamResultsService.getAll();
        List<StudentExamResultsDto> studentExamResultsDtoList = new ArrayList<>();

            studentExamResultsList.
                    forEach(result -> {
                        StudentExamResultsDto studentExamResultsDto = converter.convert(result);
                        studentExamResultsDtoList.add(studentExamResultsDto);
                    });

        return studentExamResultsDtoList;
    }

    public StudentExamResultsDto get(long id) {

        StudentExamResults studentExamResults = studentExamResultsService.get(id);

        return converter.convert(studentExamResults);
    }

    public StudentExamResultsDto replace(StudentExamResultsDto updatedStudentExamResultsDto, long id) {

        StudentExamResults updatedStudentExamResults = reverseConverter.convert(updatedStudentExamResultsDto);

        StudentExamResults newStudentExamResults = studentExamResultsService.replace(updatedStudentExamResults, id);

        return converter.convert(newStudentExamResults);
    }

    public void delete(long id) {
        studentExamResultsService.delete(id);
    }

}
