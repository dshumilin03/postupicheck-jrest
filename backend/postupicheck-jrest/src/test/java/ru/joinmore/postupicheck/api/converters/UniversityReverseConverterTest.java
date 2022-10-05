package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;

import static org.assertj.core.api.Assertions.assertThat;

class UniversityReverseConverterTest {

    private UniversityReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new UniversityReverseConverter();
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        UniversityDto universityDto = new UniversityDto(1, "testName");

        // when
        University result = testInstance.convert(universityDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");

    }
}