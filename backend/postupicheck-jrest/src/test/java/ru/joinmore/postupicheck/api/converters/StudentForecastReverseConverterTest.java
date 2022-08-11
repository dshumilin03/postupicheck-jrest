package ru.joinmore.postupicheck.api.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.StudentForecastDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.services.AdmissionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentForecastReverseConverterTest {

    private StudentForecastReverseConverter testInstance;
    @Mock
    private AdmissionService admissionService;

    @BeforeEach
    void setUp() {
        testInstance = new StudentForecastReverseConverter(admissionService);
    }

    @Test
    void shouldReturnConvertedEntity() {
        // given
        long id = 4;
        long admissionId = 2;
        Admission admission = new Admission();
        admission.setId(2L);
        StudentForecastDto studentForecastDto = new StudentForecastDto(id, admissionId);
        when(admissionService.get(admissionId)).thenReturn(admission);

        // when
        StudentForecast result = testInstance.convert(studentForecastDto);

        // then
        assertThat(result.getId()).isEqualTo(4);
        assertThat(result.getAdmission().getId()).isEqualTo(2);
    }
}