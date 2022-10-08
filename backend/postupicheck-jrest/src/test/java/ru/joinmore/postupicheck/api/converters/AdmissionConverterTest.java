package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AdmissionConverterTest {

    private AdmissionConverter testInstance;

    @BeforeEach
    void setUp() {testInstance = new AdmissionConverter();}

    @Test
    void shouldReturnConvertedDto() {
        // given
        int points = 123;
        Student student = new Student();
        Course course = new Course();

        Admission admission = new Admission(student, course, points);
        admission.setId(0L);
        student.setId(0L);
        course.setId(0L);

        // when
        AdmissionDto result = testInstance.convert(admission);

        //then
        assertThat(result.getId()).isEqualTo(0L);
        assertThat(result.getStudentId()).isEqualTo(0L);
        assertThat(result.getCourseId()).isEqualTo(0L);
        assertThat(result.getPoints()).isEqualTo(123);

    }

    @Test
    void shouldReturnConvertedList() {
        // given
        List<Admission> admissions = createAdmissionList();

        // when
        List<AdmissionDto> result = testInstance.convert(admissions);

        // then
        AdmissionDto createdDto1 = result.get(0);
        AdmissionDto createdDto2 = result.get(1);

        assertThat(createdDto1.getId()).isEqualTo(0L);
        assertThat(createdDto1.getStudentId()).isEqualTo(0L);
        assertThat(createdDto1.getCourseId()).isEqualTo(0L);
        assertThat(createdDto1.getPoints()).isEqualTo(123);

        assertThat(createdDto2.getId()).isEqualTo(1L);
        assertThat(createdDto2.getStudentId()).isEqualTo(1L);
        assertThat(createdDto2.getCourseId()).isEqualTo(1L);
        assertThat(createdDto2.getPoints()).isEqualTo(244);
    }

    private List<Admission> createAdmissionList() {
        int points1 = 123;
        int points2 = 244;

        Student student = new Student();
        student.setId(0L);

        Student student2 = new Student();
        student2.setId(1L);

        Course course = new Course();
        course.setId(0L);

        Course course2 = new Course();
        course2.setId(1L);

        Admission admission = new Admission(student, course, points1);
        admission.setId(0L);

        Admission admission2 = new Admission(student2, course2, points2);
        admission2.setId(1L);

        List<Admission> admissions = new ArrayList<>();
        admissions.add(admission);
        admissions.add(admission2);

        return admissions;
    }
}