package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.AdmissionConverter;
import ru.joinmore.postupicheck.api.converters.StudentConverter;
import ru.joinmore.postupicheck.api.converters.StudentReverseConverter;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentForecast;
import ru.joinmore.postupicheck.api.services.StudentAdmissionService;
import ru.joinmore.postupicheck.api.services.StudentForecastService;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentFacadeTest {

    private StudentFacade testInstance;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentConverter converter;
    @Mock
    private StudentReverseConverter reverseConverter;
    @Mock
    private StudentAdmissionService studentAdmissionService;
    @Mock
    private AdmissionConverter admissionConverter;
    @Mock
    private StudentForecastService studentForecastService;

    @BeforeEach
    void setUp() {
        testInstance = new StudentFacade(
                studentService,
                studentForecastService,
                studentAdmissionService,
                converter,
                reverseConverter,
                admissionConverter);
    }

    @Test
    void shouldCallStudentServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        Student student = mock(Student.class);

        when(studentService.get(id)).thenReturn(student);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(student);
    }

    @Test
    void shouldReturnConvertedStudent_WhenGet() {
        // given
        long id = 5L;
        Student student = mock(Student.class);
        StudentDto convertedStudent = mock(StudentDto.class);

        when(studentService.get(id)).thenReturn(student);
        when(converter.convert(student)).thenReturn(convertedStudent);

        // when
        StudentDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedStudent);
    }

    @Test
    void shouldCallConvertListAndStudentService_WhenGetAll() {
        // given
        List<Student> studentList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(studentService).getAll();
        verify(converter).convert(studentList);
    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<Student> studentList = new ArrayList<>();

        StudentDto studentDto1 = mock(StudentDto.class);
        StudentDto studentDto2 = mock(StudentDto.class);
        StudentDto studentDto3 = mock(StudentDto.class);

        List<StudentDto> convertedList = new ArrayList<>();
        convertedList.add(studentDto1);
        convertedList.add(studentDto2);
        convertedList.add(studentDto3);

        when(studentService.getAll()).thenReturn(studentList);
        when(converter.convert(studentList)).thenReturn(convertedList);

        // when
        List<StudentDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(studentDto1, studentDto2, studentDto3);
    }

    @Test
    void shouldCallReverseConverterAndStudentServiceAndConverter_WhenCreate() {
        // given
        StudentDto newStudentDto = mock(StudentDto.class);
        Student newStudent = mock(Student.class);
        Student createdStudent = mock(Student.class);

        when(reverseConverter.convert(newStudentDto)).thenReturn(newStudent);
        when(studentService.create(newStudent)).thenReturn(createdStudent);

        // when
        testInstance.create(newStudentDto);

        // then
        verify(converter).convert(createdStudent);
    }

    @Test
    void shouldReturnConvertedStudent_WhenCreate() {
        // given
        StudentDto newStudentDto = mock(StudentDto.class);
        Student newStudent = mock(Student.class);
        Student createdStudent = mock(Student.class);
        StudentDto convertedStudent = mock(StudentDto.class);

        when(reverseConverter.convert(newStudentDto)).thenReturn(newStudent);
        when(studentService.create(newStudent)).thenReturn(createdStudent);
        when(converter.convert(createdStudent)).thenReturn(convertedStudent);

        // when
        StudentDto result = testInstance.create(newStudentDto);

        // then
        assertThat(result).isEqualTo(convertedStudent);
    }

    @Test
    void shouldCallReverseConverterAndStudentServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        StudentDto updatedStudentDto = mock(StudentDto.class);
        Student updatedStudent = mock(Student.class);
        Student newStudent = mock(Student.class);

        when(reverseConverter.convert(updatedStudentDto)).thenReturn(updatedStudent);
        when(studentService.replace(updatedStudent, id)).thenReturn(newStudent);

        // when
        testInstance.replace(updatedStudentDto, id);

        // then
        verify(converter).convert(newStudent);
    }

    @Test
    void shouldReturnConvertedStudent_WhenReplace() {
        // given
        long id = 515L;
        StudentDto updatedStudentDto = mock(StudentDto.class);
        Student updatedStudent = mock(Student.class);
        Student newStudent = mock(Student.class);
        StudentDto convertedStudent = mock(StudentDto.class);

        when(reverseConverter.convert(updatedStudentDto)).thenReturn(updatedStudent);
        when(studentService.replace(updatedStudent, id)).thenReturn(newStudent);
        when(converter.convert(newStudent)).thenReturn(convertedStudent);

        // when
        StudentDto result = testInstance.replace(updatedStudentDto, id);

        // then
        assertThat(result).isEqualTo(convertedStudent);
    }


    @Test
    void shouldCallStudentServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(studentService).delete(id);
    }

    @Test
    void shouldCallStudentAdmissionServiceAndAdmissionConverter_WhenGetStudentAdmissions() {
        // given
        long id = 5L;
        List<Admission> admissions = new ArrayList<>();

        when(studentAdmissionService.getStudentAdmissions(id)).thenReturn(admissions);

        // when
        testInstance.getStudentAdmissions(id);

        // then
        verify(admissionConverter).convert(admissions);
    }

    @Test
    void shouldReturnConvertedStudentAdmissions_WhenGetStudentAdmissions() {
        // given
        long id = 5L;
        List<Admission> admissions = new ArrayList<>();
        List<AdmissionDto> convertedAdmissions = new ArrayList<>();

        when(admissionConverter.convert(admissions)).thenReturn(convertedAdmissions);

        // when
        List<AdmissionDto> result = testInstance.getStudentAdmissions(id);

        // then
        assertThat(result).isEqualTo(convertedAdmissions);
    }

    @Test
    void shouldCallStudentAdmissionServiceAndAdmissionConverter_WhenGetStudentAvailableAdmissions() {
        // given
        long id = 5L;
        List<Admission> admissions = new ArrayList<>();

        when(studentAdmissionService.getStudentAvailableAdmissions(id)).thenReturn(admissions);

        // when
        testInstance.getStudentAvailableAdmissions(id);

        // then
        verify(admissionConverter).convert(admissions);
    }

    @Test
    void shouldReturnConvertedStudentAdmissions_WhenGetStudentAvailableAdmissions() {
        // given
        long id = 5L;
        List<Admission> admissions = new ArrayList<>();
        List<AdmissionDto> convertedAdmissions = new ArrayList<>();

        when(admissionConverter.convert(admissions)).thenReturn(convertedAdmissions);

        // when
        List<AdmissionDto> result = testInstance.getStudentAvailableAdmissions(id);

        // then
        assertThat(result).isEqualTo(convertedAdmissions);

    }

    @Test
    void shouldCallStudentAdmissionServiceAndAdmissionConverter_WhenGetStudentConsentAdmission() {
        // given
        long id = 5L;
        Admission admission = mock(Admission.class);

        when(studentAdmissionService.getStudentConsentAdmission(id)).thenReturn(admission);

        // when
        testInstance.getStudentConsentAdmission(id);

        // then
        verify(admissionConverter).convert(admission);
    }

    @Test
    void shouldReturnConvertedStudentAdmissions_WhenGetStudentConsentAdmission() {
        // given
        long id = 5L;
        Admission admission = mock(Admission.class);
        AdmissionDto convertedAdmission = mock(AdmissionDto.class);

        when(studentAdmissionService.getStudentConsentAdmission(id)).thenReturn(admission);
        when(admissionConverter.convert(admission)).thenReturn(convertedAdmission);

        // when
        AdmissionDto result = testInstance.getStudentConsentAdmission(id);

        // then
        assertThat(result).isEqualTo(convertedAdmission);
    }

    @Test
    void shouldCallStudentForecastServiceAndAdmissionConverter_WhenGetStudentForecastAdmission() {
        // given
        long id = 5L;
        StudentForecast forecast = mock(StudentForecast.class);
        Admission admission = forecast.getAdmission();

        when(studentForecastService.getStudentForecast(id)).thenReturn(forecast);

        // when
        testInstance.getStudentForecastAdmission(id);

        // then
        verify(studentForecastService).getStudentForecast(id);
        verify(admissionConverter).convert(admission);
    }

    @Test
    void shouldReturnConvertedStudentForecastAdmission_WhenGetStudentForecastAdmission() {
        // given
        long id = 5L;
        Admission forecast = mock(Admission.class);
        StudentForecast studentForecast = new StudentForecast();
        studentForecast.setAdmission(forecast);
        AdmissionDto convertedAdmission = mock(AdmissionDto.class);

        when(studentForecastService.getStudentForecast(id)).thenReturn(studentForecast);
        when(admissionConverter.convert(forecast)).thenReturn(convertedAdmission);

        // when
        AdmissionDto result = testInstance.getStudentForecastAdmission(id);

        // then
        assertThat(result).isEqualTo(convertedAdmission);
    }
}