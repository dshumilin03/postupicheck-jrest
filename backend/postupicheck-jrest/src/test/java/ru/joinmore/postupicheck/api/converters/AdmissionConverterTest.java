package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AdmissionConverterTest {
    private AdmissionConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new AdmissionConverter();

    }
    @Test
    void convert() {
        //given
        Student student = new Student();
        Course course = new Course();
        Admission admission = new Admission(student, course);
        admission.setId(0L);
        student.setId(0L);
        course.setId(0L);

        //when
        AdmissionDto createdDto = underTest.convert(admission);
        //then

        assertThat(createdDto.getId()).isEqualTo(0L);
        assertThat(createdDto.getStudentId()).isEqualTo(0L);
        assertThat(createdDto.getCourseId()).isEqualTo(0L);

    }

    @Test
    void convertList() {
        //given
        List<Admission> admissions = new ArrayList<>();
        Student student = new Student();
        Student student2 = new Student();
        Course course = new Course();
        Course course2 = new Course();
        Admission admission = new Admission(student, course);
        Admission admission2 = new Admission(student2, course2);
        admission.setId(0L);
        admission2.setId(1L);
        student.setId(0L);
        student2.setId(1L);
        course.setId(0L);
        course2.setId(1L);

        admissions.add(admission);
        admissions.add(admission2);

        //when
        List<AdmissionDto> createdDtoList = underTest.convert(admissions);
        //then
        AdmissionDto createdDto1 = createdDtoList.get(0);
        AdmissionDto createdDto2 = createdDtoList.get(1);

        assertThat(createdDto1.getId()).isEqualTo(0L);
        assertThat(createdDto1.getStudentId()).isEqualTo(0L);
        assertThat(createdDto1.getCourseId()).isEqualTo(0L);

        assertThat(createdDto2.getId()).isEqualTo(1L);
        assertThat(createdDto2.getStudentId()).isEqualTo(1L);
        assertThat(createdDto2.getCourseId()).isEqualTo(1L);

    }

}