package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityFacadeTest {

    @Mock
    UniversityService universityService;
    @Mock
    UniversityConverter converter;
    @Mock
    UniversityReverseConverter reverseConverter;

    private UniversityFacade testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new UniversityFacade(universityService, converter, reverseConverter);
    }

    @Test
    void shouldCallUniversityServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        University university = mock(University.class);
        when(universityService.get(id)).thenReturn(university);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(university);

    }

    @Test
    void shouldReturnConvertedUniversity_WhenGet() {
        // given
        long id = 5L;
        University university = mock(University.class);
        UniversityDto convertedUniversity = mock(UniversityDto.class);
        when(universityService.get(id)).thenReturn(university);
        when(converter.convert(university)).thenReturn(convertedUniversity);

        // when
        UniversityDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedUniversity);

    }

    @Test
    void shouldCallConvertListAndUniversityService_WhenGetAll() {
        // given
        List<University> universityList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(universityService).getAll();
        verify(converter).convert(universityList);

    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<University> universityList = new ArrayList<>();
        List<UniversityDto> convertedList = new ArrayList<>();
        UniversityDto universityDto1 = mock(UniversityDto.class);
        UniversityDto universityDto2 = mock(UniversityDto.class);
        UniversityDto universityDto3 = mock(UniversityDto.class);
        convertedList.add(universityDto1);
        convertedList.add(universityDto2);
        convertedList.add(universityDto3);
        when(universityService.getAll()).thenReturn(universityList);
        when(converter.convert(universityList)).thenReturn(convertedList);

        // when
        List<UniversityDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(universityDto1, universityDto2, universityDto3);

    }

    @Test
    void shouldCallReverseConverterAndUniversityServiceAndConverter_WhenCreate() {
        // given
        UniversityDto newUniversityDto = mock(UniversityDto.class);
        University newUniversity = mock(University.class);
        University createdUniversity = mock(University.class);
        when(reverseConverter.convert(newUniversityDto)).thenReturn(newUniversity);
        when(universityService.create(newUniversity)).thenReturn(createdUniversity);

        // when
        testInstance.create(newUniversityDto);

        // then
        verify(converter).convert(createdUniversity);
    }

    @Test
    void shouldReturnConvertedUniversity_WhenCreate() {
        // given
        UniversityDto newUniversityDto = mock(UniversityDto.class);
        University newUniversity = mock(University.class);
        University createdUniversity = mock(University.class);
        UniversityDto convertedUniversity = mock(UniversityDto.class);
        when(reverseConverter.convert(newUniversityDto)).thenReturn(newUniversity);
        when(universityService.create(newUniversity)).thenReturn(createdUniversity);
        when(converter.convert(createdUniversity)).thenReturn(convertedUniversity);

        // when
        UniversityDto result = testInstance.create(newUniversityDto);

        // then
        assertThat(result).isEqualTo(convertedUniversity);
    }

    @Test
    void shouldCallReverseConverterAndUniversityServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        UniversityDto updatedUniversityDto = mock(UniversityDto.class);
        University updatedUniversity = mock(University.class);
        University newUniversity = mock(University.class);
        when(reverseConverter.convert(updatedUniversityDto)).thenReturn(updatedUniversity);
        when(universityService.replace(updatedUniversity, id)).thenReturn(newUniversity);

        // when
        testInstance.replace(updatedUniversityDto, id);

        // then
        verify(converter).convert(newUniversity);
    }

    @Test
    void shouldReturnConvertedUniversity_WhenReplace() {
        // given
        long id = 515L;
        UniversityDto updatedUniversityDto = mock(UniversityDto.class);
        University updatedUniversity = mock(University.class);
        University newUniversity = mock(University.class);
        UniversityDto convertedUniversity = mock(UniversityDto.class);
        when(reverseConverter.convert(updatedUniversityDto)).thenReturn(updatedUniversity);
        when(universityService.replace(updatedUniversity, id)).thenReturn(newUniversity);
        when(converter.convert(newUniversity)).thenReturn(convertedUniversity);

        // when
        UniversityDto result = testInstance.replace(updatedUniversityDto, id);

        // then
        assertThat(result).isEqualTo(convertedUniversity);
    }


    @Test
    void shouldCallUniversityServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(universityService).delete(id);
    }
}