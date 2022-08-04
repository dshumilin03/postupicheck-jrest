package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UniversityReverseConverterTest {

    private UniversityReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new UniversityReverseConverter();
    }

    @Test
    void convert() {
        //given
        UniversityDto universityDto = new UniversityDto(1, "testName");
        //when
        University createdDao = testInstance.convert(universityDto);
        //then
        assertThat(createdDao.getId()).isEqualTo(1L);
        assertThat(createdDao.getName()).isEqualTo("testName");

    }
}