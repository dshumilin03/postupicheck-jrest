package ru.joinmore.postupicheck.api.converters;

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
    void setUp() {testInstance = new CourseConverter();}

    @Test
    void shouldReturnConvertedEntity() {
        // given
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

        // when
        CourseDto result = testInstance.convert(course);

        // then
        assertThat(result.getId()).isEqualTo(0L);
        assertThat(result.getName()).isEqualTo("testName");
        assertThat(result.getCode()).isEqualTo("12354");
        assertThat(result.getFirstSubjectId()).isEqualTo(0L);
        assertThat(result.getSecondSubjectId()).isEqualTo(1L);
        assertThat(result.getThirdSubjectId()).isEqualTo(2L);
        assertThat(result.getUniversityId()).isEqualTo(0L);
        assertThat((result.getCurPassingPoints())).isEqualTo(231);
        assertThat(result.getBudgetPlaces()).isEqualTo(14);
    }

    @Test
    void shouldReturnConvertedDtoList() {
        // given
        List<Course> courses = createCourses();

        // when
        List<CourseDto> result = testInstance.convert(courses);

        // then
        CourseDto createdDto1 = result.get(0);
        assertThat(createdDto1.getId()).isEqualTo(0L);
        assertThat(createdDto1.getName()).isEqualTo("testName1");
        assertThat(createdDto1.getCode()).isEqualTo("12354");
        assertThat(createdDto1.getFirstSubjectId()).isEqualTo(0L);
        assertThat(createdDto1.getSecondSubjectId()).isEqualTo(1L);
        assertThat(createdDto1.getThirdSubjectId()).isEqualTo(2L);
        assertThat(createdDto1.getUniversityId()).isEqualTo(0L);
        assertThat((createdDto1.getCurPassingPoints())).isEqualTo(231);
        assertThat(createdDto1.getBudgetPlaces()).isEqualTo(54);

        CourseDto createdDto2 = result.get(1);
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

    private List<Course> createCourses() {
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

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        return courses;
    }
}