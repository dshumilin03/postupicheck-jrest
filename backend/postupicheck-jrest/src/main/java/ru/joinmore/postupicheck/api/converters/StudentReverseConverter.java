package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

@Component
public class StudentReverseConverter implements Converter<StudentDto, Student> {

    @Override
    public Student convert(StudentDto studentDto) {
        String studentName = studentDto.getName();
        String studentSnils = studentDto.getSnils();
        long studentId = studentDto.getId();
        boolean preferential = studentDto.isPreferential();

        return new Student(studentId, studentName, studentSnils, preferential);

    }
}
