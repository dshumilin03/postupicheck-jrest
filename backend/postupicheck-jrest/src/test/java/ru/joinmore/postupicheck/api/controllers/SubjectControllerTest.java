package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.SubjectDto;
import ru.joinmore.postupicheck.api.facades.SubjectFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    private SubjectController testInstance;
    @Mock
    SubjectFacade subjectFacade;

    @BeforeEach
    void setUp() {testInstance = new SubjectController(subjectFacade);}

    @Test
    void shouldReturnAllStudentsUniversityDto() {
        // given
        List<SubjectDto> subjectDtoList = createSubjectDtoList();
        SubjectDto subjectDto1 = subjectDtoList.get(0);
        SubjectDto subjectDto2 = subjectDtoList.get(1);

        when(subjectFacade.getAll()).thenReturn(subjectDtoList);

        // when
        List<SubjectDto> result = testInstance.getAllSubjects();

        // then
        assertThat(result).contains(subjectDto1, subjectDto2);
    }

    @Test
    void shouldReturnCreatedUniversityDto() {
        // given
        SubjectDto subjectDto = mock(SubjectDto.class);

        when(subjectFacade.create(subjectDto)).thenReturn(subjectDto);

        // when
        SubjectDto result = testInstance.createSubject(subjectDto);

        // then
        assertThat(result).isEqualTo(subjectDto);
    }

    @Test
    void shouldReturnSubject() {
        // given
        long subjectId = 214L;
        SubjectDto subjectDto = mock(SubjectDto.class);

        when(subjectFacade.get(subjectId)).thenReturn(subjectDto);

        // when
        SubjectDto result = testInstance.getSubject(subjectId);

        // then
        assertThat(result).isEqualTo(subjectDto);
    }

    @Test
    void shouldReturnReplacedSubject() {
        // given
        long subjectId = 124L;
        SubjectDto updatedSubjectDto = mock(SubjectDto.class);

        when(subjectFacade.replace(updatedSubjectDto, subjectId))
                .thenReturn(updatedSubjectDto);

        // when
        SubjectDto result = testInstance.replaceSubject(updatedSubjectDto, subjectId);

        // then
        assertThat(result).isEqualTo(updatedSubjectDto);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long subjectId = 124L;

        // when
        testInstance.deleteSubject(subjectId);

        // then
        verify(subjectFacade).delete(subjectId);
    }

    private List<SubjectDto> createSubjectDtoList() {
        SubjectDto subjectDto1 = mock(SubjectDto.class);
        SubjectDto subjectDto2 = mock(SubjectDto.class);

        List<SubjectDto> subjectDtoList = new ArrayList<>();
        subjectDtoList.add(subjectDto1);
        subjectDtoList.add(subjectDto2);

        return subjectDtoList;
    }
}