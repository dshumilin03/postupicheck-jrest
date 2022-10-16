package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new StudentService(studentRepository);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void shouldReturnAllStudents_WhenGetAll() {
        //given
        List<Student> students = createStudentList();

        when(studentRepository.findAll()).thenReturn(students);

        // when
        List<Student> result = testInstance.getAll();

        // then
        assertThat(result).isEqualTo(students);
    }

    @Test
    void shouldCallRepositoryFindById() {
        // given
        long id = 1L;
        Student student = mock(Student.class);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // when
        testInstance.get(id);

        // then
        verify(studentRepository).findById(id);
    }

    @Test
    void shouldReturnStudent_WhenFindById() {
        // given
        long id = 1L;
        Student student = mock(Student.class);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // when
        Student result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(student);
    }

    @Test
    void shouldThrowResourceNotExists_WhenFindById() {
        // given
        long id = 1L;

        // when
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() ->
                testInstance.get(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student with id [1]");
    }

    @Test
    void shouldSaveStudentIfNotExists() {
        // given
        String snils = "132";
        Student student = new Student();
        student.setSnils(snils);

        when(studentRepository.existsStudentBySnils(snils)).thenReturn(false);

        // when
        testInstance.create(student);

        // then
        verify(studentRepository).save(student);
    }

    @Test
    void shouldReturnSavedStudent() {
        // given
        String snils = "14425";
        Student student = new Student();
        student.setSnils(snils);

        when(studentRepository.existsStudentBySnils(snils)).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        // when
        Student result = testInstance.create(student);

        // then
        assertThat(result).isEqualTo(student);
    }

    @Test
    void shouldNotSaveStudentIfExists() {
        // given
        String snils = "4332";
        Student student = mock(Student.class);

        // when
        when(studentRepository.existsStudentBySnils(snils)).thenReturn(true);

        // then
        assertThatThrownBy(() ->
                testInstance
                        .create(student));
        verify(studentRepository, never()).save(student);
    }

    @Test
    void shouldThrowAlreadyExistsExceptionIfStudentExists() {
        // given
        String snils = "234234";
        Student student = new Student();
        student.setSnils(snils);

        // when
        when(studentRepository.existsStudentBySnils(snils)).thenReturn(true);

        // then
        assertThatThrownBy(() ->
                testInstance
                        .create(student))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("Snils 234234");
    }

    @Test
    void shouldReplaceOldStudentByNewStudent() {
        // given
        Student oldStudent = mock(Student.class);
        String newSnils = "324";
        String newName = "newName";
        Boolean newPreferential = true;
        Student newStudent = new Student(newName, newSnils, newPreferential);
        long id = 2L;

        when(studentRepository.findById(id)).thenReturn(Optional.of(oldStudent));

        // when
        testInstance.replace(newStudent, id);

        // then
        InOrder inOrder = inOrder(oldStudent, studentRepository);
        inOrder.verify(oldStudent).setName(newName);
        inOrder.verify(oldStudent).setSnils(newSnils);
        inOrder.verify(oldStudent).setPreferential(newPreferential);
        inOrder.verify(studentRepository).save(oldStudent);
    }

    @Test
    void shouldReturnReplacedStudent() {
        // given
        Student oldStudent = mock(Student.class);
        long id = 2L;

        when(studentRepository.findById(id)).thenReturn(Optional.of(oldStudent));
        when(studentRepository.save(oldStudent)).thenReturn(oldStudent);

        // when
        Student result = testInstance.replace(new Student(), id);

        // then
        assertThat(result).isEqualTo(oldStudent);
    }

    @Test
    void shouldNotReplaceStudentIfNotExists() {
        // given
        Student oldStudent = mock(Student.class);
        long id = 2;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> testInstance.replace(new Student(), id));

        // then
        verify(studentRepository, never()).save(oldStudent);
    }

    @Test
    void shouldThrowResourceIsNotExistsException_WhenReplace() {
        // given
        long id = 2L;

        // when
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() ->
                testInstance.replace(new Student(), id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student with id [2]");
    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 4L;

        // when
        testInstance.delete(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotExitsException_WhenDelete() {
        // given
        long id = 13L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(studentRepository).deleteById(id);

        // then
        assertThatThrownBy(() ->
                testInstance
                        .delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Student with id [13]");
    }

    private List<Student> createStudentList() {
        Student student1 = mock(Student.class);
        Student student2 = mock(Student.class);
        Student student3 = mock(Student.class);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        return students;
    }
}