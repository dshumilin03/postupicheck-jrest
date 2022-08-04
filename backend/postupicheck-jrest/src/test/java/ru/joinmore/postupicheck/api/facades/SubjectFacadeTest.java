package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
    void shouldCallConverterAndSubjectServiceWhenGivenId() {
        //given
        long id = 5;
        Subject subject = new Subject("math");
        when(subjectService.get(id)).thenReturn(subject);

        //when
        testInstance.get(id);

        //then
        verify(converter).convert(subject);
    }

    @Test
    void shouldReturnConvertedSubjectsWhenGetAll() {
        //given
        long id = 5;
        Subject subject = new Subject("math");
        when(subjectService.get(id)).thenReturn(subject);
        SubjectDto convertedSubject = mock(SubjectDto.class);
        when(converter.convert(subject)).thenReturn(convertedSubject);
        //when
        SubjectDto result = testInstance.get(id);

        //then
        assertThat(result).isEqualTo(convertedSubject);
    }

    @Test
    void getAll() {
        //given
        List<Subject> allSubjects = new ArrayList<>();
        when(subjectService.getAll()).thenReturn(allSubjects);

        //when
        testInstance.getAll();

        //then
        verify(converter).convert(allSubjects);
    }

    @Test
    void create() {
    }

    @Test
    void replace() {
    }

    @Test
    void delete() {
    }
}