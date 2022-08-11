package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.facades.AdmissionFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdmissionControllerTest {

    private AdmissionController testInstance;
    @Mock
    AdmissionFacade admissionFacade;

    @BeforeEach
    void setUp() {testInstance = new AdmissionController(admissionFacade);}

    @Test
    void shouldReturnAllAdmissionsDto() {
        // given
        List<AdmissionDto> admissionsDto = createAdmissionsDto();
        AdmissionDto admissionDto1 = admissionsDto.get(0);
        AdmissionDto admissionDto2 = admissionsDto.get(1);

        when(admissionFacade.getAll()).thenReturn(admissionsDto);

        // when
        testInstance.getAllAdmissions();

        // then
        assertThat(admissionsDto).contains(admissionDto1, admissionDto2);
    }

    @Test
    void shouldReturnCreatedAdmissionDto() {
        // given
        AdmissionDto admissionDto = mock(AdmissionDto.class);

        when(admissionFacade.create(admissionDto)).thenReturn(admissionDto);

        // when
        AdmissionDto result = testInstance.createAdmission(admissionDto);

        // then
        assertThat(result).isEqualTo(admissionDto);
    }

    @Test
    void shouldReturnAdmissionDto() {
        // given
        long admissionId = 123L;
        AdmissionDto admissionDto = mock(AdmissionDto.class);

        when(admissionFacade.get(admissionId)).thenReturn(admissionDto);

        // when
        AdmissionDto result = testInstance.getAdmission(admissionId);

        // then
        assertThat(result).isEqualTo(admissionDto);
    }

    @Test
    void shouldReturnCourseAdmissionsDto() {
        // given
        long courseId = 214L;
        List<AdmissionDto> admissionsDto = createAdmissionsDto();
        AdmissionDto admissionDto1 = admissionsDto.get(0);
        AdmissionDto admissionDto2 = admissionsDto.get(1);

        when(admissionFacade.getCourseAdmissions(courseId)).thenReturn(admissionsDto);

        // when
        List<AdmissionDto> result = testInstance.getCourseAdmissions(courseId);

        // then
        assertThat(result).contains(admissionDto1, admissionDto2);
    }

    @Test
    void shouldReplaceAdmissionDto() {
        // given
        long admissionId = 124L;
        AdmissionDto updatedAdmissionDto = mock(AdmissionDto.class);

        when(admissionFacade.replace(updatedAdmissionDto, admissionId)).thenReturn(updatedAdmissionDto);

        // when
        AdmissionDto result = testInstance.replaceAdmission(updatedAdmissionDto, admissionId);

        // then
        assertThat(result).isEqualTo(updatedAdmissionDto);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long admissionId = 325L;

        // when
        testInstance.deleteAdmission(admissionId);

        // then
        verify(admissionFacade).delete(admissionId);
    }

    @Test
    void shouldCallFacadeToDeleteAll() {
        // when
        testInstance.deleteAll();

        // then
        verify(admissionFacade).deleteAll();
    }

    private List<AdmissionDto> createAdmissionsDto() {
        AdmissionDto admission1 = mock(AdmissionDto.class);
        AdmissionDto admission2 = mock(AdmissionDto.class);

        List<AdmissionDto> admissionsDto = new ArrayList<>();
        admissionsDto.add(admission1);
        admissionsDto.add(admission2);

        return admissionsDto;
    }
}