package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.facades.StudentExamResultFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentExamResultControllerTest {

    private StudentExamResultController testInstance;
    @Mock
    StudentExamResultFacade studentExamResultFacade;

    @BeforeEach
    void setUp() {testInstance = new StudentExamResultController(studentExamResultFacade);}

    @Test
    void shouldReturnAllStudentsExamResultDto() {
        // given
        long studentExamResultId = 134L;
        List<StudentExamResultDto> studentExamResultDtoList = createStudentExamResultsDto();
        StudentExamResultDto studentExamResultDto1 = studentExamResultDtoList.get(0);
        StudentExamResultDto studentExamResultDto2 = studentExamResultDtoList.get(1);

        when(studentExamResultFacade.getAllStudentResults(studentExamResultId)).thenReturn(studentExamResultDtoList);

        // when
        List<StudentExamResultDto> result = testInstance.getAllStudentResults(studentExamResultId);

        // then
        assertThat(result).contains(studentExamResultDto1, studentExamResultDto2);
    }

    @Test
    void shouldReturnAllStudentExamResultsDto() {
        // given
        List<StudentExamResultDto> studentExamResultDtoList = createStudentExamResultsDto();
        StudentExamResultDto studentExamResultDto1 = studentExamResultDtoList.get(0);
        StudentExamResultDto studentExamResultDto2 = studentExamResultDtoList.get(1);

        when(studentExamResultFacade.getAll()).thenReturn(studentExamResultDtoList);

        // when
        List<StudentExamResultDto> result = testInstance.getAllResults();

        // then
        assertThat(result).contains(studentExamResultDto1, studentExamResultDto2);
    }

    @Test
    void shouldReturnCreatedStudentExamResultDto() {
        StudentExamResultDto studentExamResultDto = mock(StudentExamResultDto.class);

        when(studentExamResultFacade.create(studentExamResultDto)).thenReturn(studentExamResultDto);

        // when
        StudentExamResultDto result = testInstance.createExamResult(studentExamResultDto);

        // then
        assertThat(result).isEqualTo(studentExamResultDto);
    }

    @Test
    void shouldReturnStudentExamResultDto() {
        // given
        long studentExamResultId = 235L;
        StudentExamResultDto studentExamResultDto = mock(StudentExamResultDto.class);

        when(studentExamResultFacade.get(studentExamResultId)).thenReturn(studentExamResultDto);

        // when
        StudentExamResultDto result = testInstance.getExamResult(studentExamResultId);

        // then
        assertThat(result).isEqualTo(studentExamResultDto);
    }

    @Test
    void shouldReturnReplacedStudentExamResultDto() {
        // given
        long studentExamResultId = 423L;
        StudentExamResultDto updatedStudentExamResult = mock(StudentExamResultDto.class);

        when(studentExamResultFacade.replace(updatedStudentExamResult, studentExamResultId))
                .thenReturn(updatedStudentExamResult);

        // when
        StudentExamResultDto result = testInstance.replaceExamResult(updatedStudentExamResult, studentExamResultId);

        // then
        assertThat(result).isEqualTo(updatedStudentExamResult);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long studentExamResultId = 432L;

        // when
        testInstance.deleteExamResult(studentExamResultId);

        // then
        verify(studentExamResultFacade).delete(studentExamResultId);
    }

    private List<StudentExamResultDto> createStudentExamResultsDto() {
        StudentExamResultDto studentExamResultDto1 = mock(StudentExamResultDto.class);
        StudentExamResultDto studentExamResultDto2 = mock(StudentExamResultDto.class);

        List<StudentExamResultDto> studentExamResultDtoList = new ArrayList<>();
        studentExamResultDtoList.add(studentExamResultDto1);
        studentExamResultDtoList.add(studentExamResultDto2);

        return studentExamResultDtoList;
    }
}