package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.AdmissionConverter;
import ru.joinmore.postupicheck.api.converters.AdmissionReverseConverter;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.services.AdmissionService;
import ru.joinmore.postupicheck.api.services.CourseAdmissionService;
import ru.joinmore.postupicheck.api.services.CourseService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdmissionFacadeTest {

    private AdmissionFacade testInstance;
    @Mock
    private AdmissionService admissionService;
    @Mock
    private AdmissionConverter converter;
    @Mock
    private CourseAdmissionService courseAdmissionService;
    @Mock
    private AdmissionReverseConverter reverseConverter;

    @BeforeEach
    void setUp() {
        testInstance = new AdmissionFacade(admissionService, courseAdmissionService, converter, reverseConverter);
    }

    @Test
    void shouldCallAdmissionServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        Admission admission = mock(Admission.class);
        when(admissionService.get(id)).thenReturn(admission);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(admission);

    }

    @Test
    void shouldReturnConvertedAdmission_WhenGet() {
        // given
        long id = 5L;
        Admission admission = mock(Admission.class);
        AdmissionDto convertedAdmission = mock(AdmissionDto.class);
        when(admissionService.get(id)).thenReturn(admission);
        when(converter.convert(admission)).thenReturn(convertedAdmission);

        // when
        AdmissionDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedAdmission);

    }

    @Test
    void shouldCallConvertListAndAdmissionService_WhenGetAll() {
        // given
        List<Admission> admissionList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(admissionService).getAll();
        verify(converter).convert(admissionList);

    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<Admission> admissionList = new ArrayList<>();
        List<AdmissionDto> convertedList = new ArrayList<>();
        AdmissionDto admissionDto1 = mock(AdmissionDto.class);
        AdmissionDto admissionDto2 = mock(AdmissionDto.class);
        AdmissionDto admissionDto3 = mock(AdmissionDto.class);
        convertedList.add(admissionDto1);
        convertedList.add(admissionDto2);
        convertedList.add(admissionDto3);
        when(admissionService.getAll()).thenReturn(admissionList);
        when(converter.convert(admissionList)).thenReturn(convertedList);

        // when
        List<AdmissionDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(admissionDto1, admissionDto2, admissionDto3);

    }

    @Test
    void shouldCallReverseConverterAndAdmissionServiceAndConverter_WhenCreate() {
        // given
        AdmissionDto newAdmissionDto = mock(AdmissionDto.class);
        Admission newAdmission = mock(Admission.class);
        Admission createdAdmission = mock(Admission.class);
        when(reverseConverter.convert(newAdmissionDto)).thenReturn(newAdmission);
        when(admissionService.create(newAdmission)).thenReturn(createdAdmission);

        // when
        testInstance.create(newAdmissionDto);

        // then
        verify(converter).convert(createdAdmission);
    }

    @Test
    void shouldReturnConvertedAdmission_WhenCreate() {
        // given
        AdmissionDto newAdmissionDto = mock(AdmissionDto.class);
        Admission newAdmission = mock(Admission.class);
        Admission createdAdmission = mock(Admission.class);
        AdmissionDto convertedAdmission = mock(AdmissionDto.class);
        when(reverseConverter.convert(newAdmissionDto)).thenReturn(newAdmission);
        when(admissionService.create(newAdmission)).thenReturn(createdAdmission);
        when(converter.convert(createdAdmission)).thenReturn(convertedAdmission);

        // when
        AdmissionDto result = testInstance.create(newAdmissionDto);

        // then
        assertThat(result).isEqualTo(convertedAdmission);
    }

    @Test
    void shouldCallReverseConverterAndAdmissionServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        AdmissionDto updatedAdmissionDto = mock(AdmissionDto.class);
        Admission updatedAdmission = mock(Admission.class);
        Admission newAdmission = mock(Admission.class);
        when(reverseConverter.convert(updatedAdmissionDto)).thenReturn(updatedAdmission);
        when(admissionService.replace(updatedAdmission, id)).thenReturn(newAdmission);

        // when
        testInstance.replace(updatedAdmissionDto, id);

        // then
        verify(converter).convert(newAdmission);
    }

    @Test
    void shouldReturnConvertedAdmission_WhenReplace() {
        // given
        long id = 515L;
        AdmissionDto updatedAdmissionDto = mock(AdmissionDto.class);
        Admission updatedAdmission = mock(Admission.class);
        Admission newAdmission = mock(Admission.class);
        AdmissionDto convertedAdmission = mock(AdmissionDto.class);
        when(reverseConverter.convert(updatedAdmissionDto)).thenReturn(updatedAdmission);
        when(admissionService.replace(updatedAdmission, id)).thenReturn(newAdmission);
        when(converter.convert(newAdmission)).thenReturn(convertedAdmission);

        // when
        AdmissionDto result = testInstance.replace(updatedAdmissionDto, id);

        // then
        assertThat(result).isEqualTo(convertedAdmission);
    }


    @Test
    void shouldCallAdmissionServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(admissionService).delete(id);
    }

    @Test
    void shouldCallCourseServiceDeleteAll() {
        // when
        testInstance.deleteAll();

        // then
        verify(admissionService).deleteAll();
    }

    @Test
    void shouldReturnCourseAdmissions() {
        // given
        long id = 124L;
        List<Admission> admissions = new ArrayList<>();
        Admission admission = mock(Admission.class);
        admissions.add(admission);
        List<AdmissionDto> admissionDtoList = new ArrayList<>();
        AdmissionDto admissionDto = mock(AdmissionDto.class);
        admissionDtoList.add(admissionDto);
        when(courseAdmissionService.getCourseAdmissions(id)).thenReturn(admissions);
        when(converter.convert(admissions)).thenReturn(admissionDtoList);

        // when
        List<AdmissionDto> result = testInstance.getCourseAdmissions(id);

        // then
        assertThat(result).contains(admissionDto);
    }
}