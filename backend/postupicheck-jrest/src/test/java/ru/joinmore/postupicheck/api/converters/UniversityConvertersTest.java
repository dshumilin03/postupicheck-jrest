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
        testInstance = new UniversityConverter();}

    @Test
    void shouldReturnConvertedDto() {
        // given
        University university = new University("testName");
        university.setId(1L);

        // when
        UniversityDto result = testInstance.convert(university);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("testName");

    }

    @Test
    void shouldReturnConvertedDtoList() {
        // given
        List<University> universities = createUniversities();

        // when
        List<UniversityDto> createdDtoList = testInstance.convert(universities);

        // then
        UniversityDto universityDto1 = createdDtoList.get(0);
        assertThat(universityDto1.getId()).isEqualTo(1L);
        assertThat(universityDto1.getName()).isEqualTo("testName1");

        UniversityDto universityDto2 = createdDtoList.get(1);
        assertThat(universityDto2.getId()).isEqualTo(2L);
        assertThat(universityDto2.getName()).isEqualTo("testName2");
    }

    private List<University> createUniversities() {
        List<University> universities = new ArrayList<>();
        University university1 = new University("testName1");
        University university2 = new University("testName2");
        universities.add(university1);
        universities.add(university2);
        university2.setId(2L);
        university1.setId(1L);

        return universities;
    }
}