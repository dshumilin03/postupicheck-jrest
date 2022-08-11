package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.StudentExamResultConverter;
import ru.joinmore.postupicheck.api.converters.StudentExamResultReverseConverter;
import ru.joinmore.postupicheck.api.dto.StudentExamResultDto;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.services.StudentExamResultService;
import ru.joinmore.postupicheck.api.services.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentExamResultFacadeTest {

    private StudentExamResultFacade testInstance;
    @Mock
    private StudentExamResultService studentExamResultService;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentExamResultConverter converter;
    @Mock
    private StudentExamResultReverseConverter reverseConverter;

    @BeforeEach
    void setUp() {
        testInstance =
                new StudentExamResultFacade(studentService, converter, reverseConverter, studentExamResultService);
    }

    @Test
    void shouldCallStudentExamResultServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        StudentExamResult studentExamResult = mock(StudentExamResult.class);

        when(studentExamResultService.get(id)).thenReturn(studentExamResult);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(studentExamResult);
    }

    @Test
    void shouldReturnConvertedStudentExamResult_WhenGet() {
        // given
        long id = 5L;
        StudentExamResult studentExamResult = mock(StudentExamResult.class);
        StudentExamResultDto convertedStudentExamResult = mock(StudentExamResultDto.class);

        when(studentExamResultService.get(id)).thenReturn(studentExamResult);
        when(converter.convert(studentExamResult)).thenReturn(convertedStudentExamResult);

        // when
        StudentExamResultDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedStudentExamResult);
    }

    @Test
    void shouldCallConvertListAndStudentExamResultService_WhenGetAll() {
        // given
        List<StudentExamResult> studentExamResultList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(studentExamResultService).getAll();
        verify(converter).convert(studentExamResultList);
    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<StudentExamResult> studentExamResultList = new ArrayList<>();

        StudentExamResultDto studentExamResultDto1 = mock(StudentExamResultDto.class);
        StudentExamResultDto studentExamResultDto2 = mock(StudentExamResultDto.class);
        StudentExamResultDto studentExamResultDto3 = mock(StudentExamResultDto.class);

        List<StudentExamResultDto> convertedList = new ArrayList<>();
        convertedList.add(studentExamResultDto1);
        convertedList.add(studentExamResultDto2);
        convertedList.add(studentExamResultDto3);

        when(studentExamResultService.getAll()).thenReturn(studentExamResultList);
        when(converter.convert(studentExamResultList)).thenReturn(convertedList);

        // when
        List<StudentExamResultDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(studentExamResultDto1, studentExamResultDto2, studentExamResultDto3);
    }

    @Test
    void shouldCallReverseConverterAndStudentExamResultServiceAndConverter_WhenCreate() {
        // given
        StudentExamResultDto newStudentExamResultDto = mock(StudentExamResultDto.class);
        StudentExamResult newStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult createdStudentExamResult = mock(StudentExamResult.class);

        when(reverseConverter.convert(newStudentExamResultDto)).thenReturn(newStudentExamResult);
        when(studentExamResultService.create(newStudentExamResult)).thenReturn(createdStudentExamResult);

        // when
        testInstance.create(newStudentExamResultDto);

        // then
        verify(converter).convert(createdStudentExamResult);
    }

    @Test
    void shouldReturnConvertedStudentExamResult_WhenCreate() {
        // given
        StudentExamResultDto newStudentExamResultDto = mock(StudentExamResultDto.class);
        StudentExamResult newStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult createdStudentExamResult = mock(StudentExamResult.class);
        StudentExamResultDto convertedStudentExamResult = mock(StudentExamResultDto.class);

        when(reverseConverter.convert(newStudentExamResultDto)).thenReturn(newStudentExamResult);
        when(studentExamResultService.create(newStudentExamResult)).thenReturn(createdStudentExamResult);
        when(converter.convert(createdStudentExamResult)).thenReturn(convertedStudentExamResult);

        // when
        StudentExamResultDto result = testInstance.create(newStudentExamResultDto);

        // then
        assertThat(result).isEqualTo(convertedStudentExamResult);
    }

    @Test
    void shouldCallReverseConverterAndStudentExamResultServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        StudentExamResultDto updatedStudentExamResultDto = mock(StudentExamResultDto.class);
        StudentExamResult updatedStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult newStudentExamResult = mock(StudentExamResult.class);

        when(reverseConverter.convert(updatedStudentExamResultDto)).thenReturn(updatedStudentExamResult);
        when(studentExamResultService.replace(updatedStudentExamResult, id)).thenReturn(newStudentExamResult);

        // when
        testInstance.replace(updatedStudentExamResultDto, id);

        // then
        verify(converter).convert(newStudentExamResult);
    }

    @Test
    void shouldReturnConvertedStudentExamResult_WhenReplace() {
        // given
        long id = 515L;
        StudentExamResultDto updatedStudentExamResultDto = mock(StudentExamResultDto.class);
        StudentExamResult updatedStudentExamResult = mock(StudentExamResult.class);
        StudentExamResult newStudentExamResult = mock(StudentExamResult.class);
        StudentExamResultDto convertedStudentExamResult = mock(StudentExamResultDto.class);

        when(reverseConverter.convert(updatedStudentExamResultDto)).thenReturn(updatedStudentExamResult);
        when(studentExamResultService.replace(updatedStudentExamResult, id)).thenReturn(newStudentExamResult);
        when(converter.convert(newStudentExamResult)).thenReturn(convertedStudentExamResult);

        // when
        StudentExamResultDto result = testInstance.replace(updatedStudentExamResultDto, id);

        // then
        assertThat(result).isEqualTo(convertedStudentExamResult);
    }

    @Test
    void shouldCallStudentExamResultServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(studentExamResultService).delete(id);
    }

    @Test
    void shouldCallStudentExamResultService_WhenGetAllStudentResults() {
        // given
        long id = 5L;
        List<StudentExamResult> studentExamResults = new ArrayList<>();

        when(studentExamResultService.getAllStudentResultsByStudentId(id)).thenReturn(studentExamResults);

        // when
        testInstance.getAllStudentResults(id);

        // then
        verify(converter).convert(studentExamResults);
    }

    @Test
    void shouldReturnConvertedAllStudentExamResults_WhenGetAllStudentResults() {
        // given
        long id = 5L;
        List<StudentExamResult> studentExamResults = new ArrayList<>();
        List<StudentExamResultDto> convertedStudentExamResults = new ArrayList<>();

        when(converter.convert(studentExamResults)).thenReturn(convertedStudentExamResults);

        // when
        List<StudentExamResultDto> result = testInstance.getAllStudentResults(id);

        // then
        assertThat(result).isEqualTo(convertedStudentExamResults);
    }
}