package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityConvertersTest {

    private UniversityConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new UniversityConverter();

    }
    @Test
    void convert() {
        //given
        University university = new University("testName");
        university.setId(1L);
        //when
        UniversityDto createdDto = testInstance.convert(university);
        //then
        assertThat(createdDto.getId()).isEqualTo(1L);
        assertThat(createdDto.getName()).isEqualTo("testName");

    }

    @Test
    void convertList() {
        //given
        List<University> universities = new ArrayList<>();
        University university1 = new University("testName1");
        University university2 = new University("testName2");
        universities.add(university1);
        universities.add(university2);
        university2.setId(2L);
        university1.setId(1L);
        //when
        List<UniversityDto> createdDtoList = testInstance.convert(universities);
        //then
        assertThat(createdDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(createdDtoList.get(0).getName()).isEqualTo("testName1");
        assertThat(createdDtoList.get(1).getId()).isEqualTo(2L);
        assertThat(createdDtoList.get(1).getName()).isEqualTo("testName2");
    }
}