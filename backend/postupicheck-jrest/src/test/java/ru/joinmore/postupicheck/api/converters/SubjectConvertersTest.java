package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;

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

}