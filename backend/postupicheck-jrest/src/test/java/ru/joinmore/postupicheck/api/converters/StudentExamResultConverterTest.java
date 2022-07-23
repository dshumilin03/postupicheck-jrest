package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;

import static org.assertj.core.api.Assertions.assertThat;

class StudentExamResultConverterTest {
    private StudentExamResultConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentExamResultConverter();

    }
    @Test
    void convert() {
        //given
        Student student = new Student();
        student.setId(1L);
        Subject subject = new Subject();
        subject.setId(1L);
        StudentExamResult studentExamResult = new StudentExamResult(80, student, subject);
        studentExamResult.setId(1L);
        //when
        StudentExamResultDto createdDto = underTest.convert(studentExamResult);
        //then

        assertThat(createdDto.getId()).isEqualTo(1L);
        assertThat(createdDto.getStudentId()).isEqualTo(1L);
        assertThat(createdDto.getPoints()).isEqualTo(80);
        assertThat(createdDto.getSubjectId()).isEqualTo(1L);

    }
}