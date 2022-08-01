package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentConverterTest {


    private StudentConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentConverter();

    }
    @Test
    void convert() {
        //given
        Student student = new Student("testName", "1235");
        student.setId(1L);
        //when
        StudentDto createdDto = underTest.convert(student);
        //then
        assertThat(createdDto.getId()).isEqualTo(1L);
        assertThat(createdDto.getName()).isEqualTo("testName");
        assertThat(createdDto.getSnils()).isEqualTo("1235");

    }

    @Test
    void convertList() {
        //given
        List<Student> students = new ArrayList<>();
        Student student1 = new Student("testName1", "123");
        Student student2 = new Student("testName2", "345");
        students.add(student1);
        students.add(student2);
        student2.setId(2L);
        student1.setId(1L);
        //when
        List<StudentDto> createdDtoList = underTest.convert(students);
        //then
        assertThat(createdDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(createdDtoList.get(0).getName()).isEqualTo("testName1");
        assertThat(createdDtoList.get(0).getSnils()).isEqualTo("123");
        assertThat(createdDtoList.get(1).getId()).isEqualTo(2L);
        assertThat(createdDtoList.get(1).getName()).isEqualTo("testName2");
        assertThat(createdDtoList.get(1).getSnils()).isEqualTo("345");
    }
}