package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

@Component
public class StudentConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(Student student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSnils(student.getSnils());

        return studentDto;

    }
}
