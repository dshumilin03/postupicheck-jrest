package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.AdmissionDto;
import ru.joinmore.postupicheck.api.dto.StudentDto;
import ru.joinmore.postupicheck.api.facades.StudentFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    private StudentController testInstance;
    @Mock
    StudentFacade studentFacade;

    @BeforeEach
    void setUp() {testInstance = new StudentController(studentFacade);}

    @Test
    void shouldReturnAllStudentsDto() {
        // given
        List<StudentDto> students = createStudentsDto();
        StudentDto studentDto1 = students.get(0);
        StudentDto studentDto2 = students.get(1);

        when(studentFacade.getAll()).thenReturn(students);

        // when
        List<StudentDto> result = testInstance.getAllStudents();

        // then
        assertThat(result).contains(studentDto1, studentDto2);
    }

    @Test
    void shouldReturnCreatedStudentDto() {
        // given
        StudentDto studentDto = mock(StudentDto.class);

        when(studentFacade.create(studentDto)).thenReturn(studentDto);

        // when
        StudentDto result = testInstance.createStudent(studentDto);

        // then
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void shouldReturnStudentDto() {
        // given
        long studentId = 325L;
        StudentDto studentDto = mock(StudentDto.class);

        when(studentFacade.get(studentId)).thenReturn(studentDto);

        // when
        StudentDto result = testInstance.getStudent(studentId);

        // then
        assertThat(result).isEqualTo(studentDto);
    }

    @Test
    void shouldReturnReplacedStudentDto() {
        // given
        StudentDto updatedStudentDto = mock(StudentDto.class);
        long studentId = 643L;

        when(studentFacade.replace(updatedStudentDto, studentId)).thenReturn(updatedStudentDto);

        // when
        StudentDto result = testInstance.replaceStudent(updatedStudentDto, studentId);

        // then
        assertThat(result).isEqualTo(updatedStudentDto);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        long studentId = 52L;

        // when
        testInstance.deleteStudent(studentId);

        // then
        verify(studentFacade).delete(studentId);
    }

    @Test
    void shouldReturnStudentAdmissionsDto() {
        // given
        long studentId = 32L;
        List<AdmissionDto> studentAdmissions = createStudentAdmissionsDto();
        AdmissionDto admissionDto1 = studentAdmissions.get(0);
        AdmissionDto admissionDto2 = studentAdmissions.get(1);

        when(studentFacade.getStudentAdmissions(studentId)).thenReturn(studentAdmissions);

        // when
        List<AdmissionDto> result = testInstance.getStudentAdmissions(studentId);

        // then
        assertThat(result).contains(admissionDto1, admissionDto2);
    }

    @Test
    void shouldReturnStudentAvailableAdmissionsDto() {
        // given
        long studentId = 543L;
        List<AdmissionDto> studentAvailableAdmissions = createStudentAdmissionsDto();
        AdmissionDto admissionDto1 = studentAvailableAdmissions.get(0);
        AdmissionDto admissionDto2 = studentAvailableAdmissions.get(1);

        when(studentFacade.getStudentAvailableAdmissions(studentId)).thenReturn(studentAvailableAdmissions);

        // when
        List<AdmissionDto> result = testInstance.getStudentAvailableAdmissions(studentId);

        // then
        assertThat(result).contains(admissionDto1, admissionDto2);
    }

    @Test
    void shouldReturnStudentForecastAdmissionDto() {
        // given
        long studentId = 45L;
        AdmissionDto studentForecastAdmission = mock(AdmissionDto.class);

        when(studentFacade.getStudentForecastAdmission(studentId)).thenReturn(studentForecastAdmission);

        // when
        AdmissionDto result = testInstance.getStudentForecastAdmission(studentId);

        // then
        assertThat(result).isEqualTo(studentForecastAdmission);
    }

    @Test
    void shouldReturnStudentConsentAdmissionDto() {
        // given
        long studentId = 123L;
        AdmissionDto consentAdmissionDto = mock(AdmissionDto.class);

        when(studentFacade.getStudentConsentAdmission(studentId)).thenReturn(consentAdmissionDto);

        // when
        AdmissionDto result = testInstance.getStudentConsentAdmission(studentId);

        // then
        assertThat(result).isEqualTo(consentAdmissionDto);
    }

    private List<StudentDto> createStudentsDto() {
        StudentDto studentDto1 = mock(StudentDto.class);
        StudentDto studentDto2 = mock(StudentDto.class);

        List<StudentDto> studentDtoList = new ArrayList<>();
        studentDtoList.add(studentDto1);
        studentDtoList.add(studentDto2);

        return studentDtoList;
    }

    private List<AdmissionDto> createStudentAdmissionsDto() {
        AdmissionDto admissionDto1 = mock(AdmissionDto.class);
        AdmissionDto admissionDto2 = mock(AdmissionDto.class);

        List<AdmissionDto> admissionDtoList = new ArrayList<>();
        admissionDtoList.add(admissionDto1);
        admissionDtoList.add(admissionDto2);

        return admissionDtoList;
    }
}