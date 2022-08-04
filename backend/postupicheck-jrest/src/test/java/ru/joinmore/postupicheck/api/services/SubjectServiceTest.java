package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
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
    private SubjectService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new SubjectService(subjectRepository);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(subjectRepository).findAll();

    }

    @Test
    void shouldReturnAllSubjects_WhenGetAll() {
        //given
        List<Subject> subjects = new ArrayList<>();
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        when(subjectRepository.findAll()).thenReturn(subjects);

        // when
        List<Subject> result = testInstance.getAll();

        // then
        assertThat(result).isEqualTo(subjects);

    }

    @Test
    void shouldCallRepositoryFindById() {
        // given
        long id = 1L;
        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        // when
        testInstance.get(id);

        // then
        verify(subjectRepository).findById(id);
    }

    @Test
    void shouldReturnSubject_WhenFindById() {
        // given
        long id = 1L;
        Subject subject = mock(Subject.class);
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        // when
        Subject result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(subject);
    }

    @Test
    void shouldThrowResourceNotExists_WhenFindById() {
        // given
        long id = 1L;

        // when
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> testInstance.get(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Subject with id [1]");
    }

    @Test
    void shouldSaveSubjectIfNotExists() {
        // given
        String name = "testName";
        Subject subject = new Subject(name);
        when(subjectRepository.existsByName(name)).thenReturn(false);

        // when
        testInstance.create(subject);

        // then
        verify(subjectRepository).save(subject);

    }

    @Test
    void shouldReturnSavedSubject() {
        // given
        String name = "testName";
        Subject subject = new Subject(name);
        when(subjectRepository.existsByName(name)).thenReturn(false);
        when(subjectRepository.save(subject)).thenReturn(subject);

        // when
        Subject result = testInstance.create(subject);

        // then
        assertThat(result).isEqualTo(subject);

    }

    @Test
    void shouldNotSaveSubjectIfExists() {
        // given
        String name = "testName";
        Subject subject = new Subject(name);

        // when
        when(subjectRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(subject));
        verify(subjectRepository, never()).save(subject);

    }

    @Test
    void shouldThrowAlreadyExistsExceptionIfSubjectExists() {
        // given
        String name = "testName";
        Subject subject = new Subject(name);

        // when
        when(subjectRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(subject))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(name);

    }

    @Test
    void shouldReplaceOldSubjectByNewSubject() {

        // given
        Subject oldSubject = mock(Subject.class);
        String newName = "newName";
        Subject newSubject = new Subject(newName);
        long id = 2L;
        when(subjectRepository.findById(id)).thenReturn(Optional.of(oldSubject));

        // when
        testInstance.replace(newSubject, id);

        // then
        InOrder inOrder = inOrder(oldSubject, subjectRepository);
        inOrder.verify(oldSubject).setName(newName);
        inOrder.verify(subjectRepository).save(oldSubject);

    }

    @Test
    void shouldReturnReplacedSubject() {
        // given
        Subject oldSubject = mock(Subject.class);
        long id = 2L;
        when(subjectRepository.findById(id)).thenReturn(Optional.of(oldSubject));
        when(subjectRepository.save(oldSubject)).thenReturn(oldSubject);

        // when
        Subject result = testInstance.replace(new Subject(), id);

        // then
        assertThat(result).isEqualTo(oldSubject);

    }

    @Test
    void shouldNotReplaceSubjectIfNotExists() {
        // given
        Subject oldSubject = mock(Subject.class);
        long id = 2;
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new Subject(), id));

        // then
        verify(subjectRepository, never()).save(oldSubject);

    }

    @Test
    void shouldThrowResourceIsNotExistsException_WhenReplace() {
        // given
        long id = 2L;

        // when
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new Subject(), id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Subject with id [2]");

    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 4L;

        // when
        testInstance.delete(id);

        // then
        verify(subjectRepository).deleteById(id);

    }

    @Test
    void shouldThrowResourceNotExitsException_WhenDelete() {
        // given
        long id = 13L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(subjectRepository).deleteById(id);

        // then
        assertThatThrownBy(() -> testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Subject with id [13]");
    }

    @Test
    void shouldCallRepositoryFindByName() {
        // given
        String name = "testName";

        // when
        testInstance.findByName(name);

        // then
        verify(subjectRepository).findByName(name);
    }

    @Test
    void shouldReturnSubject_WhenFindByName() {
        // given
        String name = "testName";
        Subject subject = new Subject(name);
        when(subjectRepository.findByName(name)).thenReturn(subject);

        // when
        Subject result = testInstance.findByName(name);

        // then
        assertThat(result).isEqualTo(subject);
    }

}