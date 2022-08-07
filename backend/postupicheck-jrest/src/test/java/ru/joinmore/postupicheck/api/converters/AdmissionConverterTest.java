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
    void setUp() {
        testInstance = new AdmissionConverter();

    }
    @Test
    void convert() {
        //given
        Student student = new Student();
        Course course = new Course();
        int points = 123;
        Admission admission = new Admission(student, course, points);
        admission.setId(0L);
        student.setId(0L);
        course.setId(0L);

        //when
        AdmissionDto createdDto = testInstance.convert(admission);
        //then

        assertThat(createdDto.getId()).isEqualTo(0L);
        assertThat(createdDto.getStudentId()).isEqualTo(0L);
        assertThat(createdDto.getCourseId()).isEqualTo(0L);
        assertThat(createdDto.getPoints()).isEqualTo(123);

    }

    @Test
    void convertList() {
        //given
        List<Admission> admissions = new ArrayList<>();
        Student student = new Student();
        Student student2 = new Student();
        Course course = new Course();
        Course course2 = new Course();
        int points1 = 123;
        int points2 = 244;
        Admission admission = new Admission(student, course, points1);
        Admission admission2 = new Admission(student2, course2, points2);
        admission.setId(0L);
        admission2.setId(1L);
        student.setId(0L);
        student2.setId(1L);
        course.setId(0L);
        course2.setId(1L);

        admissions.add(admission);
        admissions.add(admission2);

        //when
        List<AdmissionDto> createdDtoList = testInstance.convert(admissions);
        //then
        AdmissionDto createdDto1 = createdDtoList.get(0);
        AdmissionDto createdDto2 = createdDtoList.get(1);

        assertThat(createdDto1.getId()).isEqualTo(0L);
        assertThat(createdDto1.getStudentId()).isEqualTo(0L);
        assertThat(createdDto1.getCourseId()).isEqualTo(0L);
        assertThat(createdDto1.getPoints()).isEqualTo(123);

        assertThat(createdDto2.getId()).isEqualTo(1L);
        assertThat(createdDto2.getStudentId()).isEqualTo(1L);
        assertThat(createdDto2.getCourseId()).isEqualTo(1L);
        assertThat(createdDto2.getPoints()).isEqualTo(244);

    }

}