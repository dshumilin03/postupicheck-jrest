package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.SubjectRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;
    private SubjectService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SubjectService(subjectRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(subjectRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        Subject subject = new Subject("testSubject");
        subject.setId(id);
        given(subjectRepository.findById(id)).willReturn(Optional.of(subject));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(subjectRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        Subject subject = new Subject("testSubject");
        //when
        underTest.create(subject);
        //then
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);

        verify(subjectRepository).save(subjectArgumentCaptor.capture());

        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(capturedSubject).isEqualTo(subject);
    }

    @Test
    void createExistingSubject() {
        //given
        Subject subject = new Subject("testSubject");
        //when
        given(subjectRepository.existsByName(subject.getName())).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(subject))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(subject.getName());

        verify(subjectRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        Subject oldSubject = new Subject("oldSubject");
        Subject newSubject = new Subject("newSubject");
        long id = anyLong();
        given(subjectRepository.findById(id)).willReturn(Optional.of(oldSubject));
        //when
        underTest.replace(newSubject, id);
        //then
        verify(subjectRepository).findById(id);

        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(capturedSubject.getName()).isEqualTo(newSubject.getName());

    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        Subject subject = new Subject();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(subjectRepository).deleteById(longArgumentCaptor.capture());
        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingStudent() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(subjectRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }

}