package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.converters.SubjectConverter;
import ru.joinmore.postupicheck.api.converters.SubjectReverseConverter;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.services.SubjectService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectFacadeTest {

    private SubjectFacade testInstance;
    @Mock
    private SubjectService subjectService;
    @Mock
    private SubjectConverter converter;
    @Mock
    private SubjectReverseConverter reverseConverter;

    @BeforeEach
    void setUp() {
        testInstance = new SubjectFacade(subjectService, converter, reverseConverter);
    }

    @Test
    void shouldCallSubjectServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        Subject subject = mock(Subject.class);
        when(subjectService.get(id)).thenReturn(subject);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(subject);
    }

    @Test
    void shouldReturnConvertedSubject_WhenGet() {
        // given
        long id = 5L;
        Subject subject = mock(Subject.class);
        SubjectDto convertedSubject = mock(SubjectDto.class);

        when(subjectService.get(id)).thenReturn(subject);
        when(converter.convert(subject)).thenReturn(convertedSubject);

        // when
        SubjectDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedSubject);
    }

    @Test
    void shouldCallConvertListAndSubjectService_WhenGetAll() {
        // given
        List<Subject> subjectList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(subjectService).getAll();
        verify(converter).convert(subjectList);
    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<Subject> subjectList = new ArrayList<>();

        SubjectDto subjectDto1 = mock(SubjectDto.class);
        SubjectDto subjectDto2 = mock(SubjectDto.class);
        SubjectDto subjectDto3 = mock(SubjectDto.class);

        List<SubjectDto> convertedList = new ArrayList<>();
        convertedList.add(subjectDto1);
        convertedList.add(subjectDto2);
        convertedList.add(subjectDto3);

        when(subjectService.getAll()).thenReturn(subjectList);
        when(converter.convert(subjectList)).thenReturn(convertedList);

        // when
        List<SubjectDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(subjectDto1, subjectDto2, subjectDto3);
    }

    @Test
    void shouldCallReverseConverterAndSubjectServiceAndConverter_WhenCreate() {
        // given
        SubjectDto newSubjectDto = mock(SubjectDto.class);
        Subject newSubject = mock(Subject.class);
        Subject createdSubject = mock(Subject.class);

        when(reverseConverter.convert(newSubjectDto)).thenReturn(newSubject);
        when(subjectService.create(newSubject)).thenReturn(createdSubject);

        // when
        testInstance.create(newSubjectDto);

        // then
        verify(converter).convert(createdSubject);
    }

    @Test
    void shouldReturnConvertedSubject_WhenCreate() {
        // given
        SubjectDto newSubjectDto = mock(SubjectDto.class);
        Subject newSubject = mock(Subject.class);
        Subject createdSubject = mock(Subject.class);
        SubjectDto convertedSubject = mock(SubjectDto.class);

        when(reverseConverter.convert(newSubjectDto)).thenReturn(newSubject);
        when(subjectService.create(newSubject)).thenReturn(createdSubject);
        when(converter.convert(createdSubject)).thenReturn(convertedSubject);

        // when
        SubjectDto result = testInstance.create(newSubjectDto);

        // then
        assertThat(result).isEqualTo(convertedSubject);
    }

    @Test
    void shouldCallReverseConverterAndSubjectServiceAndConverter_WhenReplace() {
        // given
        long id = 15L;
        SubjectDto updatedSubjectDto = mock(SubjectDto.class);
        Subject updatedSubject = mock(Subject.class);
        Subject newSubject = mock(Subject.class);

        when(reverseConverter.convert(updatedSubjectDto)).thenReturn(updatedSubject);
        when(subjectService.replace(updatedSubject, id)).thenReturn(newSubject);

        // when
        testInstance.replace(updatedSubjectDto, id);

        // then
        verify(converter).convert(newSubject);
    }

    @Test
    void shouldReturnConvertedSubject_WhenReplace() {
        // given
        long id = 515L;
        SubjectDto updatedSubjectDto = mock(SubjectDto.class);
        Subject updatedSubject = mock(Subject.class);
        Subject newSubject = mock(Subject.class);
        SubjectDto convertedSubject = mock(SubjectDto.class);

        when(reverseConverter.convert(updatedSubjectDto)).thenReturn(updatedSubject);
        when(subjectService.replace(updatedSubject, id)).thenReturn(newSubject);
        when(converter.convert(newSubject)).thenReturn(convertedSubject);

        // when
        SubjectDto result = testInstance.replace(updatedSubjectDto, id);

        // then
        assertThat(result).isEqualTo(convertedSubject);
    }

    @Test
    void shouldCallSubjectServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(subjectService).delete(id);
    }
}