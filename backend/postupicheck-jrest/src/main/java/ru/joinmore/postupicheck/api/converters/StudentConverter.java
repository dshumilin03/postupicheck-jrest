package ru.joinmore.postupicheck.api.converters;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentConverter implements Converter<Student, StudentDto>, ListConverter<Student, StudentDto> {

    @Override
    public StudentDto convert(Student student) {
        Long id = student.getId();
        String studentName = student.getName();
        String snils = student.getSnils();
        Boolean preferential = student.isPreferential();

        return new StudentDto(id, studentName, snils, preferential);

    }

    @Override
    public List<StudentDto> convert(List<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();

        students.
                forEach(student -> {
                    StudentDto studentDto = convert(student);
                    studentDtoList.add(studentDto);
                });

        return studentDtoList;
    }
}
