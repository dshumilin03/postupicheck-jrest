package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseReverseConverterTest {

    @Mock
    UniversityService universityService;
    @Mock
    SubjectService subjectService;
    private CourseReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new CourseReverseConverter(universityService, subjectService);
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        CourseDto courseDto = new CourseDto(
                1,
                "testName",
                "testCode",
                1,
                1,
                2,
                3,
                231,
                23);
        Subject subject = new Subject(1L, "testSubject1");
        Subject subject2 = new Subject(2L, "testSubject2");
        Subject subject3 = new Subject(3L, "testSubject3");
        University university = new University(1L, "testSubject");

        when(universityService.get(1L)).thenReturn(university);
        when(subjectService.get(1L)).thenReturn(subject);
        when(subjectService.get(2L)).thenReturn(subject2);
        when(subjectService.get(3L)).thenReturn(subject3);

        // when
        Course result = testInstance.convert(courseDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");
        assertThat(result.getCode()).isEqualTo("testCode");
        assertThat(result.getUniversity().getId()).isEqualTo(1);
        assertThat(result.getFirstSubject().getId()).isEqualTo(1);
        assertThat(result.getSecondSubject().getId()).isEqualTo(2);
        assertThat(result.getThirdSubject().getId()).isEqualTo(3);
        assertThat(result.getCurPassingPoints()).isEqualTo(231);
        assertThat(result.getBudgetPlaces()).isEqualTo(23);
    }
}