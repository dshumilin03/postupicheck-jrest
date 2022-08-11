package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class SubjectConvertersTest {

    private SubjectConverter testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new SubjectConverter();}

    @Test
    void shouldReturnConvertedDto() {
        // given
        Subject subject = new Subject("testSubject");
        subject.setId(1);

        // when
        SubjectDto result = testInstance.convert(subject);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("testSubject");
    }

    @Test
    void shouldReturnConvertedDtoList() {
        // given
        List<Subject> subjects = createSubjects();

        // when
        List<SubjectDto> result = testInstance.convert(subjects);

        // then
        SubjectDto subjectDto1 = result.get(0);
        assertThat(subjectDto1.getId()).isEqualTo(1L);
        assertThat(subjectDto1.getName()).isEqualTo("testName1");

        SubjectDto subjectDto2 = result.get(1);
        assertThat(subjectDto2.getId()).isEqualTo(2L);
        assertThat(subjectDto2.getName()).isEqualTo("testName2");
    }

    private List<Subject> createSubjects() {
        Subject subject1 = new Subject("testName1");
        subject1.setId(1L);
        Subject subject2 = new Subject("testName2");
        subject2.setId(2L);

        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject1);
        subjects.add(subject2);

        return subjects;
    }
}