package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentExamResultConverterTest {
    private StudentExamResultConverter testInstance;

    @BeforeEach
    void setUp() {testInstance = new StudentExamResultConverter();}

    @Test
    void shouldReturnConvertedEntity() {
        // given
        Student student = new Student();
        student.setId(1L);
        Subject subject = new Subject();
        subject.setId(1L);
        StudentExamResult studentExamResult = new StudentExamResult(80, student, subject);
        studentExamResult.setId(1L);

        // when
        StudentExamResultDto result = testInstance.convert(studentExamResult);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudentId()).isEqualTo(1L);
        assertThat(result.getPoints()).isEqualTo(80);
        assertThat(result.getSubjectId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnConvertedEntityList() {
        // given
        List<StudentExamResult> studentExamResults = createStudentExamResults();

        // when
        List<StudentExamResultDto> result = testInstance.convert(studentExamResults);

        // then
        StudentExamResultDto createdDto1 = result.get(0);
        StudentExamResultDto createdDto2 = result.get(1);
        assertThat(createdDto1.getId()).isEqualTo(1L);
        assertThat(createdDto1.getStudentId()).isEqualTo(1L);
        assertThat(createdDto1.getPoints()).isEqualTo(80);
        assertThat(createdDto1.getSubjectId()).isEqualTo(1L);

        assertThat(createdDto2.getId()).isEqualTo(2L);
        assertThat(createdDto2.getStudentId()).isEqualTo(2L);
        assertThat(createdDto2.getPoints()).isEqualTo(85);
        assertThat(createdDto2.getSubjectId()).isEqualTo(2L);
    }

    private List<StudentExamResult> createStudentExamResults() {
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

        List<StudentExamResult> studentExamResults = new ArrayList<>();
        studentExamResults.add(studentExamResult1);
        studentExamResults.add(studentExamResult2);

        return studentExamResults;
    }
}