package ru.joinmore.postupicheck.api.converters;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CourseConverterTest {

    private CourseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new CourseConverter();

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
        int budgetPlaces = 14;

        Course course = new Course(
                "testName",
                "12354",
                university,
                firstSubject,
                secondSubject,
                thirdSubject,
                curPassingPoints,
                budgetPlaces);
        course.setId(0L);
        //when
        CourseDto createdDto = testInstance.convert(course);
        //then

        assertThat(createdDto.getId()).isEqualTo(0L);
        assertThat(createdDto.getName()).isEqualTo("testName");
        assertThat(createdDto.getCode()).isEqualTo("12354");
        assertThat(createdDto.getFirstSubjectId()).isEqualTo(0L);
        assertThat(createdDto.getSecondSubjectId()).isEqualTo(1L);
        assertThat(createdDto.getThirdSubjectId()).isEqualTo(2L);
        assertThat(createdDto.getUniversityId()).isEqualTo(0L);
        assertThat((createdDto.getCurPassingPoints())).isEqualTo(231);
        assertThat(createdDto.getBudgetPlaces()).isEqualTo(14);

    }

    @Test
    void convertList() {
        //given
        List<Course> courses = new ArrayList<>();

        University university1 = new University();
        University university2 = new University();
        university1.setId(0L);
        university2.setId(1L);

        Subject firstSubject = new Subject();
        firstSubject.setId(0L);
        Subject secondSubject = new Subject();
        secondSubject.setId(1L);
        Subject thirdSubject = new Subject();
        thirdSubject.setId(2L);
        int curPassingPoints1 = 231;
        int curPassingPoints2 = 211;
        int budgetPlaces1 = 54;
        int budgetPlaces2 = 34;

        Course course1 = new Course(
                "testName1",
                "12354",
                university1,
                firstSubject,
                secondSubject,
                thirdSubject,
                curPassingPoints1,
                budgetPlaces1);
        Course course2 = new Course(
                "testName2",
                "12355",
                university2,
                firstSubject,
                secondSubject,
                thirdSubject,
                curPassingPoints2,
                budgetPlaces2);
        course1.setId(0L);
        course2.setId(1L);
        courses.add(course1);
        courses.add(course2);
        //when
        List<CourseDto> createdDtoList = testInstance.convert(courses);
        //then
        CourseDto createdDto1 = createdDtoList.get(0);
        CourseDto createdDto2 = createdDtoList.get(1);

        assertThat(createdDto1.getId()).isEqualTo(0L);
        assertThat(createdDto1.getName()).isEqualTo("testName1");
        assertThat(createdDto1.getCode()).isEqualTo("12354");
        assertThat(createdDto1.getFirstSubjectId()).isEqualTo(0L);
        assertThat(createdDto1.getSecondSubjectId()).isEqualTo(1L);
        assertThat(createdDto1.getThirdSubjectId()).isEqualTo(2L);
        assertThat(createdDto1.getUniversityId()).isEqualTo(0L);
        assertThat((createdDto1.getCurPassingPoints())).isEqualTo(231);
        assertThat(createdDto1.getBudgetPlaces()).isEqualTo(54);

        assertThat(createdDto2.getId()).isEqualTo(1L);
        assertThat(createdDto2.getName()).isEqualTo("testName2");
        assertThat(createdDto2.getCode()).isEqualTo("12355");
        assertThat(createdDto2.getFirstSubjectId()).isEqualTo(0L);
        assertThat(createdDto2.getSecondSubjectId()).isEqualTo(1L);
        assertThat(createdDto2.getThirdSubjectId()).isEqualTo(2L);
        assertThat(createdDto2.getUniversityId()).isEqualTo(1L);
        assertThat((createdDto2.getCurPassingPoints())).isEqualTo(211);
        assertThat(createdDto2.getBudgetPlaces()).isEqualTo(34);

    }
}