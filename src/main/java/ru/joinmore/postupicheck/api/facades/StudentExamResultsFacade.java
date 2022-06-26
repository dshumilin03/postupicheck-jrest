package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.models.Student;
import ru.joinmore.postupicheck.api.models.StudentExamResults;
import ru.joinmore.postupicheck.api.models.Subject;
import ru.joinmore.postupicheck.api.services.StudentExamResultsService;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentExamResultsFacade {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentExamResultsService studentExamResultsService;


    public StudentExamResultsFacade(StudentService studentService,
                                    SubjectService subjectService,
                                    StudentExamResultsService studentExamResultsService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.studentExamResultsService = studentExamResultsService;

    }

    public StudentExamResultsDto create(StudentExamResultsDto examResultsDto) {

        Subject subject = subjectService.get(examResultsDto.getSubjectId());
        examResultsDto.setSubjectId(subject.getId());

        Student student = studentService.get(examResultsDto.getStudentId());
        examResultsDto.setStudentId(student.getId());


        int result = examResultsDto.getResult();
        StudentExamResults studentExamResults = new StudentExamResults(result, student, subject);
        studentExamResultsService.create(studentExamResults);

        return examResultsDto;

    }

    public List<StudentExamResultsDto> getAll() {

        List<StudentExamResults> studentExamResultsList = studentExamResultsService.getAll();
        List<StudentExamResultsDto> studentExamResultsDtoList = new ArrayList<>();

        for (StudentExamResults result: studentExamResultsList
             ) {
            StudentExamResultsDto studentExamResultsDto = new StudentExamResultsDto();
            studentExamResultsDto.setResult(result.getResult());
            studentExamResultsDto.setStudentId(result.getSubject().getId());
            studentExamResultsDto.setSubjectId(result.getStudent_id().getId());
            studentExamResultsDtoList.add(studentExamResultsDto);

        }
        return studentExamResultsDtoList;
    }
}
