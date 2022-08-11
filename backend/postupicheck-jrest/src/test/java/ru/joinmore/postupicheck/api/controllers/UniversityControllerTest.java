package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.UniversityDto;
import ru.joinmore.postupicheck.api.facades.UniversityFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UniversityControllerTest {

    private UniversityController testInstance;
    @Mock
    UniversityFacade universityFacade;

    @BeforeEach
    void setUp() {testInstance = new UniversityController(universityFacade);}

    @Test
    void shouldReturnAllStudentsUniversityDto() {
        // given
        List<UniversityDto> universityDtoList = createUniversityDtoList();
        UniversityDto universityDto1 = universityDtoList.get(0);
        UniversityDto universityDto2 = universityDtoList.get(1);

        when(universityFacade.getAll()).thenReturn(universityDtoList);

        // when
        List<UniversityDto> result = testInstance.getAllUniversities();

        // then
        assertThat(result).contains(universityDto1, universityDto2);
    }

    @Test
    void shouldReturnCreatedUniversityDto() {
        // given
        UniversityDto universityDto = mock(UniversityDto.class);

        when(universityFacade.create(universityDto)).thenReturn(universityDto);

        // when
        UniversityDto result = testInstance.createUniversity(universityDto);

        // then
        assertThat(result).isEqualTo(universityDto);
    }

    @Test
    void shouldReturnUniversity() {
        // given
        long universityId = 214L;
        UniversityDto universityDto = mock(UniversityDto.class);

        when(universityFacade.get(universityId)).thenReturn(universityDto);

        // when
        UniversityDto result = testInstance.getUniversity(universityId);

        // then
        assertThat(result).isEqualTo(universityDto);
    }

    @Test
    void shouldReturnReplacedUniversity() {
        // given
        long universityId = 124L;
        UniversityDto updatedUniversityDto = mock(UniversityDto.class);

        when(universityFacade.replace(updatedUniversityDto, universityId))
                .thenReturn(updatedUniversityDto);

        // when
        UniversityDto result = testInstance.replaceUniversity(updatedUniversityDto, universityId);

        // then
        assertThat(result).isEqualTo(updatedUniversityDto);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long universityId = 124L;

        // when
        testInstance.deleteUniversity(universityId);

        // then
        verify(universityFacade).delete(universityId);
    }

    private List<UniversityDto> createUniversityDtoList() {
        UniversityDto universityDto1 = mock(UniversityDto.class);
        UniversityDto universityDto2 = mock(UniversityDto.class);

        List<UniversityDto> universityDtoList = new ArrayList<>();
        universityDtoList.add(universityDto1);
        universityDtoList.add(universityDto2);

        return universityDtoList;
    }
}