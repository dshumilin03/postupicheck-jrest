package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.StudentService;
import ru.joinmore.postupicheck.api.services.SubjectService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudentExamResultReverseConverterTest {
    
    @Mock
    StudentService studentService;
    @Mock
    SubjectService subjectService;
    private StudentExamResultReverseConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentExamResultReverseConverter(subjectService, studentService);
    }

    @Test
    void convert() {
        //given
        StudentExamResultDto studentExamResultDto = new StudentExamResultDto(1, 1, 1, 58);
        Student student = new Student(1L, "testName", "1234");
        Subject subject = new Subject(1L, "testSubject");

        given(studentService.get(1L)).willReturn(student);
        given(subjectService.get(1L)).willReturn(subject);
        //when
        StudentExamResult createdDao = underTest.convert(studentExamResultDto);
        //then
        assertThat(createdDao.getId()).isEqualTo(1L);
        assertThat(createdDao.getStudent().getId()).isEqualTo(1);
        assertThat(createdDao.getSubject().getId()).isEqualTo(1);
        assertThat(createdDao.getPoints()).isEqualTo(58);

    }

}