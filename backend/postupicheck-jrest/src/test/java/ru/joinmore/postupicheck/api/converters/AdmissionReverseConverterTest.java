package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdmissionReverseConverterTest {

    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    private AdmissionReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new AdmissionReverseConverter(studentService, courseService);
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        AdmissionDto admissionDto = new AdmissionDto(1, 1, 1,  false, 123);
        Student student = new Student(1L, "testName", "1234");
        Course course = new Course();
        course.setId(1L);

        when(studentService.get(1L)).thenReturn(student);
        when(courseService.get(1L)).thenReturn(course);

        // when
        Admission result = testInstance.convert(admissionDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudent().getId()).isEqualTo(1);
        assertThat(result.getCourse().getId()).isEqualTo(1);
        assertThat(result.getPoints()).isEqualTo(123);
    }
}