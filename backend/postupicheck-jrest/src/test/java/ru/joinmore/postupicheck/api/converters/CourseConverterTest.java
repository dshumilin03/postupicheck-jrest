package ru.joinmore.postupicheck.api.converters;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import static org.assertj.core.api.Assertions.assertThat;

class CourseConverterTest {

    private CourseConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new CourseConverter();

    }
    @Test
    void convert() {
        //given
        University university = new University();
        university.setId(0L);

        Subject firstSubject = new Subject();
        firstSubject.setId(0L);
        Subject secondSubject = new Subject();
        secondSubject.setId(1L);
        Subject thirdSubject = new Subject();
        thirdSubject.setId(2L);
        int curPassingPoints = 231;

        Course course = new Course(
                "testName",
                "12354",
                university,
                firstSubject,
                secondSubject,
                thirdSubject,
                curPassingPoints);
        course.setId(0L);
        //when
        CourseDto createdDto = underTest.convert(course);
        //then

        assertThat(createdDto.getId()).isEqualTo(0L);
        assertThat(createdDto.getName()).isEqualTo("testName");
        assertThat(createdDto.getCode()).isEqualTo("12354");
        assertThat(createdDto.getFirstSubjectId()).isEqualTo(0L);
        assertThat(createdDto.getSecondSubjectId()).isEqualTo(1L);
        assertThat(createdDto.getThirdSubjectId()).isEqualTo(2L);
        assertThat(createdDto.getUniversityId()).isEqualTo(0L);
        assertThat((createdDto.getCurPassingPoints())).isEqualTo(231);

    }
}