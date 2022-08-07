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
import static org.mockito.BDDMockito.given;

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
    void convert() {
        //given
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

        given(universityService.get(1L)).willReturn(university);
        given(subjectService.get(1L)).willReturn(subject);
        given(subjectService.get(2L)).willReturn(subject2);
        given(subjectService.get(3L)).willReturn(subject3);
        //when
        Course createdDao = testInstance.convert(courseDto);
        //then
        assertThat(createdDao.getId()).isEqualTo(1L);
        assertThat(createdDao.getName()).isEqualTo("testName");
        assertThat(createdDao.getCode()).isEqualTo("testCode");
        assertThat(createdDao.getUniversity().getId()).isEqualTo(1);
        assertThat(createdDao.getFirstSubject().getId()).isEqualTo(1);
        assertThat(createdDao.getSecondSubject().getId()).isEqualTo(2);
        assertThat(createdDao.getThirdSubject().getId()).isEqualTo(3);
        assertThat(createdDao.getCurPassingPoints()).isEqualTo(231);
        assertThat(createdDao.getBudgetPlaces()).isEqualTo(23);

    }

}