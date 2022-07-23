package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

@Component
public class StudentConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(Student student) {

        Long id = student.getId();
        String studentName = student.getName();
        String snils = student.getSnils();

        return new StudentDto(id, studentName, snils);

    }
}
