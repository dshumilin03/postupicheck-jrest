package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.CourseRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    private CourseService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CourseService(courseRepository);
    }

    @Test
    void getAll() {
        //when
        underTest.getAll();
        //then
        verify(courseRepository).findAll();

    }

    @Test
    void get() {
        //given
        long id = anyLong();
        String name = "testName";
        String code = "12334";
        University university = new University("testUniversity");
        Subject firstSubject = new Subject("testSubject");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");

        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);
        course.setId(id);

        given(courseRepository.findById(id)).willReturn(Optional.of(course));
        //when
        underTest.get(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(courseRepository).findById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void create() {
        //given
        String name = "testName";
        String code = "12334";
        University university = new University("testUniversity");
        Subject firstSubject = new Subject("testSubject");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");

        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);
        //when
        underTest.create(course);
        //then
        ArgumentCaptor<Course> CourseArgumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepository).save(CourseArgumentCaptor.capture());

        Course capturedCourse = CourseArgumentCaptor.getValue();

        assertThat(capturedCourse).isEqualTo(course);
    }

    @Test
    void createExistingCourse() {
        //given
        String name = "testName";
        String code = "12334";
        University university = new University("testUniversity");
        Subject firstSubject = new Subject("testSubject");
        Subject secondSubject = new Subject("testSubject2");
        Subject thirdSubject = new Subject("testSubject3");

        Course course = new Course(name, code, university, firstSubject, secondSubject, thirdSubject);
        //when
        given(courseRepository.existsByCode(code)).willReturn(true);
        //then
        assertThatThrownBy(() -> underTest.create(course))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(course.getCode());

        verify(courseRepository, never()).save(any());
    }

    @Test
    void replace() {
        //given
        String oldName = "testName";
        String newName = "testName2";

        String oldCode = "12334";
        String newCode = "23456";

        University oldUniversity = new University("testUniversity");
        University newUniversity = new University("testUniversity2");

        Subject oldFirstSubject = new Subject("testSubject1");
        Subject newFirstSubject = new Subject("testSubject11");

        Subject oldSecondSubject = new Subject("testSubject2");
        Subject newSecondSubject = new Subject("testSubject222");

        Subject oldThirdSubject = new Subject("testSubject3");
        Subject newThirdSubject = new Subject("testSubject333");

        Course oldCourse = new Course(
                oldName,
                oldCode, oldUniversity,
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

        long id = anyLong();

        given(courseRepository.findById(id)).willReturn(Optional.of(oldCourse));
        //when
        underTest.replace(newCourse, id);
        //then
        verify(courseRepository).findById(id);

        ArgumentCaptor<Course> CourseArgumentCaptor = ArgumentCaptor.forClass(Course.class);

        verify(courseRepository).save(CourseArgumentCaptor.capture());
        Course capturedCourse = CourseArgumentCaptor.getValue();

        assertThat(capturedCourse.getName()).isEqualTo(newCourse.getName());
        assertThat(capturedCourse.getCode()).isEqualTo(newCourse.getCode());
        assertThat(capturedCourse.getUniversity()).isEqualTo(newCourse.getUniversity());
        assertThat(capturedCourse.getFirstSubject()).isEqualTo(newCourse.getFirstSubject());
        assertThat(capturedCourse.getSecondSubject()).isEqualTo(newCourse.getSecondSubject());
        assertThat(capturedCourse.getThirdSubject()).isEqualTo(newCourse.getThirdSubject());
    }

    @Test
    void delete() {
        //given
        long id = anyLong();
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(courseRepository).deleteById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(id);
    }

    @Test
    void deleteNotExistingCourse() {
        long id = -1L;
        //given
        //when
        doThrow(new EmptyResultDataAccessException(-1)).when(courseRepository).deleteById(id);
        //then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("is not exists");

    }
}