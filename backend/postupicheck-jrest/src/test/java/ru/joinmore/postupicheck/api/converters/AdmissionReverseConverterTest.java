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
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdmissionReverseConverterTest {



    @Mock
    UniversityService universityService;
    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    private AdmissionReverseConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new AdmissionReverseConverter(studentService, courseService);
    }

    @Test
    void convert() {
        //given
        AdmissionDto admissionDto = new AdmissionDto(1, 1, 1,  false);
        Student student = new Student(1L, "testName", "1234");
        Course course = new Course();
        course.setId(1L);

        given(studentService.get(1L)).willReturn(student);
        given(courseService.get(1L)).willReturn(course);
        //when
        Admission createdDao = underTest.convert(admissionDto);
        //then
        assertThat(createdDao.getId()).isEqualTo(1L);
        assertThat(createdDao.getStudent().getId()).isEqualTo(1);
        assertThat(createdDao.getCourse().getId()).isEqualTo(1);

    }
}