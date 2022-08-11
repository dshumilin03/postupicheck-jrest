package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;

import static org.assertj.core.api.Assertions.assertThat;

class SubjectReverseConverterTest {
    private SubjectReverseConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new SubjectReverseConverter();
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        SubjectDto subjectDto = new SubjectDto(1, "testName");

        // when
        Subject result = testInstance.convert(subjectDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");
    }
}