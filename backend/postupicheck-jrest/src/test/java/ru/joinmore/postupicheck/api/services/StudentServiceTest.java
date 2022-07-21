package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(studentRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        Student student = new Student("testStudent", "123456");
        student.setId(id);
        given(studentRepository.findById(id)).willReturn(Optional.of(student));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        Student student = new Student("testStudent", "12356");
        //when
            underTest.create(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void createExistedSnils() {
        //given
        Student student = new Student("testStudent", "12356");
        //when
        given(studentRepository.existsStudentBySnils(student.getSnils())).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(student))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("Snils");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        Student oldStudent = new Student("oldStudent", "234567");
        Student newStudent = new Student("newStudent", "12346");
        long id = anyLong();
        given(studentRepository.findById(id)).willReturn(Optional.of(oldStudent));
        //when
        underTest.replace(newStudent, id);
        //then
        verify(studentRepository).findById(id);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent.getName()).isEqualTo(newStudent.getName());
        assertThat(capturedStudent.getSnils()).isEqualTo(newStudent.getSnils());


    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(longArgumentCaptor.capture());
        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingStudent() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(studentRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }
}