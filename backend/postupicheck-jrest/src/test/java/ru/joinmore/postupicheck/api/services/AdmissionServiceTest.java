package ru.joinmore.postupicheck.api.services;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.AdmissionRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdmissionServiceTest {

    @Mock
    private AdmissionRepository admissionRepository;
    private AdmissionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AdmissionService(admissionRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(admissionRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        String name = "testCourseName";
        String code = "12334";
        University university = new University("testUniversity");
        Student student = new Student("testName", "testSnils");
        Subject firstSubject = new Subject("testSubject1");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");
        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);

        Admission admission = new Admission(student, university, course);
        admission.setId(id);

        given(admissionRepository.findById(id)).willReturn(Optional.of(admission));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(admissionRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        String name = "testCourseName";
        String code = "12334";
        University university = new University("testUniversity");
        Student student = new Student("testName", "testSnils");
        Subject firstSubject = new Subject("testSubject1");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");
        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);

        Admission admission = new Admission(student, university, course);
        //when
        underTest.create(admission);
        //then
        ArgumentCaptor<Admission> AdmissionArgumentCaptor = ArgumentCaptor.forClass(Admission.class);

        verify(admissionRepository).save(AdmissionArgumentCaptor.capture());

        Admission capturedAdmission = AdmissionArgumentCaptor.getValue();

        assertThat(capturedAdmission).isEqualTo(admission);
    }

    @Test
    void createExistingAdmission() {
        //given
        String name = "testCourseName";
        String code = "12334";
        University university = new University("testUniversity");
        Student student = new Student("testName", "testSnils");
        Subject firstSubject = new Subject("testSubject1");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");
        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);

        Admission admission = new Admission(student, university, course);
        //when
        given(admissionRepository.existsByCourseAndStudent(course, student)).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(admission))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(admission.getStudent().getName())
                .hasMessageContaining(admission.getCourse().getCode());

        verify(admissionRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        String oldName = "testCourseName";
        String newName = "testCourseName2";
        String oldCode = "12334";
        String newCode = "23456";

        University oldUniversity = new University("testUniversity");
        University newUniversity = new University("testUniversity2");

        Student oldStudent = new Student("testName", "testSnils");
        Student newStudent = new Student("testName2", "testSnils2");

        Subject oldFirstSubject = new Subject("testSubject1");
        Subject newFirstSubject = new Subject("testSubject11");
        Subject oldSecondSubject = new Subject("testSubject2");
        Subject newSecondSubject = new Subject("testSubject22");
        Subject oldThirdSubject = new Subject("testSubject3");
        Subject newThirdSubject = new Subject("testSubject33");


        Course oldCourse = new Course(
                oldName,
                oldCode,
                oldUniversity,
                oldFirstSubject,
                oldSecondSubject,
                oldThirdSubject);
        Course newCourse = new Course(
                newName,
                newCode,
                newUniversity,
                newFirstSubject,
                newSecondSubject,
                newThirdSubject);

        Admission oldAdmission = new Admission(oldStudent, oldUniversity, oldCourse);
        Admission newAdmission = new Admission(newStudent, newUniversity, newCourse);
        long id = anyLong();

        given(admissionRepository.findById(id)).willReturn(Optional.of(oldAdmission));
        //when
        underTest.replace(newAdmission, id);
        //then
        verify(admissionRepository).findById(id);

        ArgumentCaptor<Admission> AdmissionArgumentCaptor = ArgumentCaptor.forClass(Admission.class);

        verify(admissionRepository).save(AdmissionArgumentCaptor.capture());
        Admission capturedAdmission = AdmissionArgumentCaptor.getValue();

        assertThat(capturedAdmission.getStudent()).isEqualTo(newAdmission.getStudent());
        assertThat(capturedAdmission.getCourse()).isEqualTo(newAdmission.getCourse());
        assertThat(capturedAdmission.getUniversity()).isEqualTo(newAdmission.getUniversity());

    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(admissionRepository).deleteById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingAdmission() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(admissionRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }
}