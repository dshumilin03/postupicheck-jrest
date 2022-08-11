package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.UniversityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Mock
    private UniversityRepository universityRepository;
    private UniversityService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new UniversityService(universityRepository);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(universityRepository).findAll();
    }

    @Test
    void shouldReturnAllUniversities_WhenGetAll() {
        //given
        List<University> universities = createUniversities();

        when(universityRepository.findAll()).thenReturn(universities);

        // when
        List<University> result = testInstance.getAll();

        // then
        assertThat(result).isEqualTo(universities);
    }

    @Test
    void shouldCallRepositoryFindById() {
        // given
        long id = 1L;
        University university = mock(University.class);

        when(universityRepository.findById(id)).thenReturn(Optional.of(university));

        // when
        testInstance.get(id);

        // then
        verify(universityRepository).findById(id);
    }

    @Test
    void shouldReturnUniversity_WhenFindById() {
        // given
        long id = 1L;
        University university = mock(University.class);

        when(universityRepository.findById(id)).thenReturn(Optional.of(university));

        // when
        University result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(university);
    }

    @Test
    void shouldThrowResourceNotExists_WhenFindById() {
        // given
        long id = 1L;

        // when
        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> testInstance.get(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("University with id [1]");
    }

    @Test
    void shouldSaveUniversityIfNotExists() {
        // given
        String name = "testName";
        University university = new University(name);

        when(universityRepository.existsByName(name)).thenReturn(false);

        // when
        testInstance.create(university);

        // then
        verify(universityRepository).save(university);
    }

    @Test
    void shouldReturnSavedUniversity() {
        // given
        String name = "testName";
        University university = new University(name);

        when(universityRepository.existsByName(name)).thenReturn(false);
        when(universityRepository.save(university)).thenReturn(university);

        // when
        University result = testInstance.create(university);

        // then
        assertThat(result).isEqualTo(university);
    }

    @Test
    void shouldNotSaveUniversityIfExists() {
        // given
        String name = "testName";
        University university = new University(name);

        // when
        when(universityRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(university));
        verify(universityRepository, never()).save(university);
    }

    @Test
    void shouldThrowAlreadyExistsExceptionIfUniversityExists() {
        // given
        String name = "testName";
        University university = new University(name);

        // when
        when(universityRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(university))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(name);
    }

    @Test
    void shouldReplaceOldUniversityByNewUniversity() {
        // given
        University oldUniversity = mock(University.class);
        String newName = "newName";
        University newUniversity = new University(newName);
        long id = 2L;

        when(universityRepository.findById(id)).thenReturn(Optional.of(oldUniversity));

        // when
        testInstance.replace(newUniversity, id);

        // then
        InOrder inOrder = inOrder(oldUniversity, universityRepository);
        inOrder.verify(oldUniversity).setName(newName);
        inOrder.verify(universityRepository).save(oldUniversity);
    }

    @Test
    void shouldReturnReplacedUniversity() {
        // given
        University oldUniversity = mock(University.class);
        long id = 2L;

        when(universityRepository.findById(id)).thenReturn(Optional.of(oldUniversity));
        when(universityRepository.save(oldUniversity)).thenReturn(oldUniversity);

        // when
        University result = testInstance.replace(new University(), id);

        // then
        assertThat(result).isEqualTo(oldUniversity);
    }

    @Test
    void shouldNotReplaceUniversityIfNotExists() {
        // given
        University oldUniversity = mock(University.class);
        long id = 2;

        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new University(), id));

        // then
        verify(universityRepository, never()).save(oldUniversity);
    }

    @Test
    void shouldThrowResourceIsNotExistsException_WhenReplace() {
        // given
        long id = 2L;

        // when
        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() ->
                testInstance
                        .replace(new University(), id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("University with id [2]");
    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 4L;

        // when
        testInstance.delete(id);

        // then
        verify(universityRepository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotExitsException_WhenDelete() {
        // given
        long id = 13L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(universityRepository).deleteById(id);

        // then
        assertThatThrownBy(() -> testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("University with id [13]");
    }

    private List<University> createUniversities() {
        University university1 = mock(University.class);
        University university2 = mock(University.class);
        University university3 = mock(University.class);

        List<University> universities = new ArrayList<>();
        universities.add(university1);
        universities.add(university2);
        universities.add(university3);

        return universities;
    }
}