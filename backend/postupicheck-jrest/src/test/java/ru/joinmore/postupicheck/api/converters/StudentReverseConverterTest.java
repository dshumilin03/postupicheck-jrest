package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentForecast;

import static org.assertj.core.api.Assertions.assertThat;

class StudentReverseConverterTest {
    private StudentReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentReverseConverter();
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        StudentDto studentDto = new StudentDto(1, "testName", "12345");

        // when
        Student result = testInstance.convert(studentDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");
        assertThat(result.getSnils()).isEqualTo("12345");
    }

}