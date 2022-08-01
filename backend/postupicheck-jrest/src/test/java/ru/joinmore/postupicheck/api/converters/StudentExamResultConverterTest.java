package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void convertList() {
        //given
        List<StudentExamResult> studentExamResults = new ArrayList<>();
        Student student = new Student();
        student.setId(1L);
        Subject subject = new Subject();
        subject.setId(1L);
        StudentExamResult studentExamResult1 = new StudentExamResult(80, student, subject);
        studentExamResult1.setId(1L);
        Student student2 = new Student();
        student2.setId(2L);
        Subject subject2 = new Subject();
        subject2.setId(2L);
        StudentExamResult studentExamResult2 = new StudentExamResult(85, student2, subject2);
        studentExamResult2.setId(2L);
        studentExamResults.add(studentExamResult1);
        studentExamResults.add(studentExamResult2);
        //when
        List<StudentExamResultDto> createdDtoList = underTest.convert(studentExamResults);
        //then
        StudentExamResultDto createdDto1 = createdDtoList.get(0);
        StudentExamResultDto createdDto2 = createdDtoList.get(1);
        assertThat(createdDto1.getId()).isEqualTo(1L);
        assertThat(createdDto1.getStudentId()).isEqualTo(1L);
        assertThat(createdDto1.getPoints()).isEqualTo(80);
        assertThat(createdDto1.getSubjectId()).isEqualTo(1L);

        assertThat(createdDto2.getId()).isEqualTo(2L);
        assertThat(createdDto2.getStudentId()).isEqualTo(2L);
        assertThat(createdDto2.getPoints()).isEqualTo(85);
        assertThat(createdDto2.getSubjectId()).isEqualTo(2L);
    }
}