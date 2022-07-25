package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.UniversityConverter;
import ru.joinmore.postupicheck.api.converters.UniversityReverseConverter;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UniversityFacadeTest {

    @Mock
    UniversityService universityService;
    @Mock
    UniversityConverter converter;
    @Mock
    UniversityReverseConverter reverseConverter;

    private UniversityFacade underTest;

    @BeforeEach
    void setUp() {
        underTest = new UniversityFacade(universityService, converter, reverseConverter);
    }

    @Test
    void get() {
        //given
        University university = new University();
        given(universityService.get(1L)).willReturn(university);
        //when
        underTest.get(1L);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<University> universityArgumentCaptor = ArgumentCaptor.forClass(University.class);

        verify(universityService).get(longArgumentCaptor.capture());
        verify(converter).convert(universityArgumentCaptor.capture());

        long capturedLong = longArgumentCaptor.getValue();
        University capturedUniversity = universityArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(1L);
        assertThat(capturedUniversity).isEqualTo(university);
    }

    @Test
    void getAll() {
        //given
        List<University> universityList = new ArrayList<>();
        University university = new University();
        universityList.add(university);
        given(universityService.getAll()).willReturn(universityList);
        //when
        underTest.getAll();
        //then
        ArgumentCaptor<University> universityArgumentCaptor = ArgumentCaptor.forClass(University.class);

        verify(universityService).getAll();
        verify(converter, times(universityList.size())).convert(universityArgumentCaptor.capture());

        List<University> capturedUniversities = universityArgumentCaptor.getAllValues();

        for (int i = 0; i < capturedUniversities.size(); i++) {
            assertThat(universityList.get(i)).isEqualTo(capturedUniversities.get(i));
        }
    }

    @Test
    void create() {
        //given
        University newUniversity = new University();
        UniversityDto newUniversityDto = new UniversityDto(1, "testName");
        University createdUniversity = new University();
        given(reverseConverter.convert(newUniversityDto)).willReturn(newUniversity);
        given(universityService.create(newUniversity)).willReturn(createdUniversity);
        //when
        underTest.create(newUniversityDto);
        //then
        verify(reverseConverter).convert(newUniversityDto);
        verify(universityService).create(newUniversity);
        verify(converter).convert(createdUniversity);
    }

    @Test
    void replace() {
        //given
        UniversityDto updatedUniversityDto = new UniversityDto(1, "testName");
        long id = 1;
        University updatedUniversity = new University();
        University newUniversity = new University();
        given(reverseConverter.convert(updatedUniversityDto)).willReturn(updatedUniversity);
        given(universityService.replace(updatedUniversity, id)).willReturn(newUniversity);
        //when
        underTest.replace(updatedUniversityDto, id);
        //then
        verify(reverseConverter).convert(updatedUniversityDto);
        verify(universityService).replace(updatedUniversity, id);
        verify(converter).convert(newUniversity);
    }

    @Test
    void delete() {
        //given
        long id = 5;
        //when
        underTest.delete(id);
        //then
        verify(universityService).delete(id);
    }
}