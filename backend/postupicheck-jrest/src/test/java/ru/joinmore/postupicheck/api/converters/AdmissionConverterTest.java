package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.*;

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
        University university = new University();
        Course course = new Course();
        Admission admission = new Admission(student, university, course);
        admission.setId(0L);
        student.setId(0L);
        university.setId(0L);
        course.setId(0L);

        //when
        AdmissionDto createdDto = underTest.convert(admission);
        //then

        assertThat(createdDto.getId()).isEqualTo(0L);
        assertThat(createdDto.getStudentId()).isEqualTo(0L);
        assertThat(createdDto.getUniversityId()).isEqualTo(0L);
        assertThat(createdDto.getCourseId()).isEqualTo(0L);

    }

}