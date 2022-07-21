package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.UniversityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Mock
    private UniversityRepository universityRepository;
    private UniversityService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UniversityService(universityRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(universityRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        University university = new University("testuniversity");
        university.setId(id);
        given(universityRepository.findById(id)).willReturn(Optional.of(university));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(universityRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        University university = new University("testUniversity");
        //when
        underTest.create(university);
        //then
        ArgumentCaptor<University> universityArgumentCaptor = ArgumentCaptor.forClass(University.class);

        verify(universityRepository).save(universityArgumentCaptor.capture());

        University captureduniversity = universityArgumentCaptor.getValue();

        assertThat(captureduniversity).isEqualTo(university);
    }

    @Test
    void createExistingUniversity() {
        //given
        University university = new University("testUniversity");
        //when
        given(universityRepository.existsByName(university.getName())).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(university))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(university.getName());

        verify(universityRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        University oldUniversity = new University("oldUniversity");
        University newUniversity = new University("newUniversity");
        long id = anyLong();
        given(universityRepository.findById(id)).willReturn(Optional.of(oldUniversity));
        //when
        underTest.replace(newUniversity, id);
        //then
        verify(universityRepository).findById(id);

        ArgumentCaptor<University> universityArgumentCaptor = ArgumentCaptor.forClass(University.class);
        verify(universityRepository).save(universityArgumentCaptor.capture());
        University capturedUniversity = universityArgumentCaptor.getValue();

        assertThat(capturedUniversity.getName()).isEqualTo(newUniversity.getName());

    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        University university = new University();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(universityRepository).deleteById(longArgumentCaptor.capture());
        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingUniversity() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(universityRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }
}