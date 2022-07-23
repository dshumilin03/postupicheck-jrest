package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

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
}