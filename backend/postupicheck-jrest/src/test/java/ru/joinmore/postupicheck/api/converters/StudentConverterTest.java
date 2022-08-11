package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Student;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentConverterTest {

    private StudentConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentConverter();

    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        Student student = new Student(1L, "testName", "1235");

        // when
        StudentDto result = testInstance.convert(student);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");
        assertThat(result.getSnils()).isEqualTo("1235");
    }

    @Test
    void shouldReturnConvertedListEntity() {
        // given
        List<Student> students = createStudents();

        // when
        List<StudentDto> result = testInstance.convert(students);

        // then
        StudentDto createdDto = result.get(0);
        StudentDto createdDto2 = result.get(1);

        assertThat(createdDto.getId()).isEqualTo(1L);
        assertThat(createdDto.getName()).isEqualTo("testName1");
        assertThat(createdDto.getSnils()).isEqualTo("123");

        assertThat(createdDto2.getId()).isEqualTo(2L);
        assertThat(createdDto2.getName()).isEqualTo("testName2");
        assertThat(createdDto2.getSnils()).isEqualTo("345");
    }

    private List<Student> createStudents() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student("testName1", "123");
        Student student2 = new Student("testName2", "345");
        students.add(student1);
        students.add(student2);
        student2.setId(2L);
        student1.setId(1L);

        return students;
    }
}