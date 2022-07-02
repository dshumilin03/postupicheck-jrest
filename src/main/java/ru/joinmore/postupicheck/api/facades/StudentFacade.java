package ru.joinmore.postupicheck.api.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.converters.StudentConverter;
import ru.joinmore.postupicheck.api.converters.StudentReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentFacade {

    private final StudentService studentService;
    private final StudentConverter converter;
    private final StudentReverseConverter reverseConverter;

    public StudentFacade(StudentService studentService,
                         StudentConverter converter,
                         StudentReverseConverter reverseConverter) {
        this.studentService = studentService;
        this.converter = converter;
        this.reverseConverter = reverseConverter;
    }

    public StudentDto get(long id) {

        Student student =  studentService.get(id);

        return converter.convert(student);
    }

    public List<StudentDto> getAll() {

        List<Student> allStudents = studentService.getAll();
        List<StudentDto> allStudentsDto = new ArrayList<>();

        allStudents.
                forEach(student -> {
                    StudentDto studentDto = converter.convert(student);
                    allStudentsDto.add(studentDto);
                });

        return allStudentsDto;
    }

    public StudentDto create(StudentDto newStudentDto) {

        Student newStudent = reverseConverter.convert(newStudentDto);
        Student createdStudent = studentService.create(newStudent);

        return converter.convert(createdStudent);
    }

    public StudentDto replace(StudentDto updatedStudentDto, long id) {

        Student updatedStudent = reverseConverter.convert(updatedStudentDto);
        Student newStudent = studentService.replace(updatedStudent, id);

        return converter.convert(newStudent);
    }

    public void delete(long id) {
        studentService.delete(id);
    }

}
