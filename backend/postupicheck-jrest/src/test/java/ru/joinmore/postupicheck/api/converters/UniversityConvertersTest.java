package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityConvertersTest {

    private UniversityConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new UniversityConverter();

    }
    @Test
    void convert() {
        //given
        University university = new University("testName");
        university.setId(1L);
        //when
        UniversityDto createdDto = underTest.convert(university);
        //then
        assertThat(createdDto.getId()).isEqualTo(1L);
        assertThat(createdDto.getName()).isEqualTo("testName");

    }
}