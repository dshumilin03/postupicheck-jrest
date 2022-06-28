package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.dto.StudentExamResultsDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.entities.Subject;
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

        StudentExamResults studentExamResults = setStudentExamResults(examResultsDto);

        StudentExamResults newExamResults = studentExamResultsService.create(studentExamResults);

        examResultsDto.setId(newExamResults.getId());


        return examResultsDto;

    }

    public List<StudentExamResultsDto> getAll() {

        List<StudentExamResults> studentExamResultsList = studentExamResultsService.getAll();
        List<StudentExamResultsDto> studentExamResultsDtoList = new ArrayList<>();

        for (StudentExamResults result: studentExamResultsList) {

            StudentExamResultsDto studentExamResultsDto = setStudentExamResultsDto(result);
            studentExamResultsDtoList.add(studentExamResultsDto);

        }
        return studentExamResultsDtoList;
    }

    public StudentExamResultsDto get(long id) {

        StudentExamResults studentExamResults = studentExamResultsService.get(id);
        Student student = studentExamResults.getStudent();
        Subject subject = studentExamResults.getSubject();

        StudentExamResultsDto studentExamResultsDto = new StudentExamResultsDto();
        studentExamResultsDto.setResult(studentExamResults.getResult());
        studentExamResultsDto.setStudentId(student.getId());
        studentExamResultsDto.setSubjectId(subject.getId());
        studentExamResultsDto.setId(studentExamResults.getId());

        return studentExamResultsDto;
    }

    public StudentExamResultsDto replace(StudentExamResultsDto updatedStudentExamResultsDto, long id) {

        StudentExamResults updatedStudentExamResults = setStudentExamResults(updatedStudentExamResultsDto);

        StudentExamResults newStudentExamResults = studentExamResultsService.replace(updatedStudentExamResults, id);

        updatedStudentExamResultsDto.setId(newStudentExamResults.getId());

        return updatedStudentExamResultsDto;
    }

    public void delete(long id) {
        studentExamResultsService.delete(id);
    }

    private StudentExamResultsDto setStudentExamResultsDto(StudentExamResults result) {

        StudentExamResultsDto studentExamResultsDto = new StudentExamResultsDto();
        studentExamResultsDto.setResult(result.getResult());
        studentExamResultsDto.setStudentId(result.getSubject().getId());
        studentExamResultsDto.setSubjectId(result.getStudent().getId());
        studentExamResultsDto.setId(result.getId());

        return studentExamResultsDto;
    }

    private StudentExamResults setStudentExamResults(StudentExamResultsDto examResultsDto) {

        long subjectId = examResultsDto.getSubjectId();
        Subject subject = subjectService.get(subjectId);

        long studentId = examResultsDto.getStudentId();
        Student student = studentService.get(studentId);

        int result = examResultsDto.getResult();

        return new StudentExamResults(result, student, subject);
    }
}
