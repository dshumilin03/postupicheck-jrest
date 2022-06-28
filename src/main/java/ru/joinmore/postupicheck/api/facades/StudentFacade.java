package ru.joinmore.postupicheck.api.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentFacade {

    private final StudentService studentService;

    public StudentFacade(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentDto get(long id) {

        Student student =  studentService.get(id);

        return setStudentDto(student);
    }

    public List<StudentDto> getAll() {

        List<Student> allStudents = studentService.getAll();
        List<StudentDto> allStudentsDto = new ArrayList<>();

        for (Student student: allStudents) {
            StudentDto studentDto = setStudentDto(student);
            allStudentsDto.add(studentDto);
        }

        return allStudentsDto;
    }

    public StudentDto create(StudentDto newStudentDto) {

        Student newStudent = setStudent(newStudentDto);
        Student createdStudent = studentService.create(newStudent);
        newStudentDto.setId(createdStudent.getId());

        return newStudentDto;
    }

    public StudentDto replace(StudentDto updatedStudentDto, long id) {

        Student updatedStudent = setStudent(updatedStudentDto);
        Student newStudent = studentService.replace(updatedStudent, id);
        updatedStudentDto.setId(newStudent.getId());

        return updatedStudentDto;
    }

    public void delete(long id) {
        studentService.delete(id);
    }

    private StudentDto setStudentDto(Student student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSnils(student.getSnils());

        return studentDto;
    }

    private Student setStudent(StudentDto StudentDto) {

        String studentName = StudentDto.getName();
        String studentSnils = StudentDto.getSnils();

        return new Student(studentName, studentSnils);
    }
}
