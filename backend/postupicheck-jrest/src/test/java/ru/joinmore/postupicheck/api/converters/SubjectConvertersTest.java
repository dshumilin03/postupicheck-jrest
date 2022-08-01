package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubjectConvertersTest {

    private SubjectConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new SubjectConverter();

    }
    @Test
    void convert() {
        //given
        Subject subject = new Subject("testSubject");
        subject.setId(1);
        //when
        SubjectDto createdDto = underTest.convert(subject);
        //then
        assertThat(createdDto.getId()).isEqualTo(1);
        assertThat(createdDto.getName()).isEqualTo("testSubject");

    }

    @Test
    void convertList() {
        //given
        List<Subject> subjects = new ArrayList<>();
        Subject subject1 = new Subject("testName1");
        Subject subject2 = new Subject("testName2");
        subjects.add(subject1);
        subjects.add(subject2);
        subject2.setId(2L);
        subject1.setId(1L);
        //when
        List<SubjectDto> createdDtoList = underTest.convert(subjects);
        //then
        assertThat(createdDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(createdDtoList.get(0).getName()).isEqualTo("testName1");
        assertThat(createdDtoList.get(1).getId()).isEqualTo(2L);
        assertThat(createdDtoList.get(1).getName()).isEqualTo("testName2");
    }

}