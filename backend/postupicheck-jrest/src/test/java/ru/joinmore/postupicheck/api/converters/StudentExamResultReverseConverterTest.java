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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentExamResultReverseConverterTest {
    
    @Mock
    StudentService studentService;
    @Mock
    SubjectService subjectService;
    private StudentExamResultReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentExamResultReverseConverter(subjectService, studentService);
    }

    @Test
    void shouldReturnConvertedDto() {
        // given
        StudentExamResultDto studentExamResultDto = new StudentExamResultDto(1, 1, 1, 58);
        Student student = new Student(1L, "testName", "1234");
        Subject subject = new Subject(1L, "testSubject");

        when(studentService.get(1L)).thenReturn(student);
        when(subjectService.get(1L)).thenReturn(subject);

        // when
        StudentExamResult result = testInstance.convert(studentExamResultDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudent().getId()).isEqualTo(1);
        assertThat(result.getSubject().getId()).isEqualTo(1);
        assertThat(result.getPoints()).isEqualTo(58);
    }

}