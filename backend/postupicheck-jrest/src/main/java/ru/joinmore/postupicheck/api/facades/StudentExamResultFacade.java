package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.StudentExamResultConverter;
import ru.joinmore.postupicheck.api.converters.StudentExamResultReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.services.StudentExamResultService;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StudentExamResultFacade {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentExamResultConverter converter;
    private final StudentExamResultReverseConverter reverseConverter;
    private final StudentExamResultService studentExamResultService;

    public StudentExamResultFacade(StudentService studentService,
                                   SubjectService subjectService,
                                   StudentExamResultConverter converter,
                                   StudentExamResultReverseConverter reverseConverter,
                                   StudentExamResultService studentExamResultService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
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

        List<StudentExamResult> studentExamResultList = studentExamResultService.getAll();
        List<StudentExamResultDto> studentExamResultDtoList = new ArrayList<>();

            studentExamResultList.
                    forEach(result -> {
                        StudentExamResultDto studentExamResultDto = converter.convert(result);
                        studentExamResultDtoList.add(studentExamResultDto);
                    });

        return studentExamResultDtoList;
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

    // Убрать, сделать полем студента
    public int getAvgScores() {
        List<StudentExamResult> allResults = studentExamResultService.getAll();
        AtomicInteger sumPoints = new AtomicInteger();
        allResults.forEach(result -> {
            sumPoints.addAndGet(result.getPoints());
        });
        return sumPoints.get() / allResults.size();
    }
}
