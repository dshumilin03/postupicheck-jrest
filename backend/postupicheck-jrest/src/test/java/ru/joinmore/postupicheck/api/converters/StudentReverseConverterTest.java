package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

import static org.assertj.core.api.Assertions.assertThat;

class StudentReverseConverterTest {
    private StudentReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentReverseConverter();
    }

    @Test
    void convert() {
        //given
        StudentDto studentDto = new StudentDto(1, "testName", "12345");
        //when
        Student createdDao = testInstance.convert(studentDto);
        //then
        assertThat(createdDao.getId()).isEqualTo(1L);
        assertThat(createdDao.getName()).isEqualTo("testName");
        assertThat(createdDao.getSnils()).isEqualTo("12345");

    }

}