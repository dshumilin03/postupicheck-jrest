package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.facades.CourseFacade;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    private CourseController testInstance;
    @Mock
    CourseFacade courseFacade;

    @BeforeEach
    void setUp() {testInstance = new CourseController(courseFacade);}

    @Test
    void shouldReturnAllCourses() {
        // given
        List<CourseDto> coursesDto = createCoursesDto();
        CourseDto courseDto1 = coursesDto.get(0);
        CourseDto courseDto2 = coursesDto.get(1);

        when(courseFacade.getAll()).thenReturn(coursesDto);

        // when
        List<CourseDto> result = testInstance.getAllCourses();

        // then
        assertThat(result).contains(courseDto1, courseDto2);
    }

    @Test
    void shouldReturnCreatedCourseDto() {
        // given
        CourseDto courseDto = mock(CourseDto.class);

        when(courseFacade.create(courseDto)).thenReturn(courseDto);

        // when
        CourseDto result = testInstance.createCourse(courseDto);

        // then
        assertThat(result).isEqualTo(courseDto);
    }

    @Test
    void shouldReturnCourseDto() {
        // given
        long courseId = 155L;
        CourseDto courseDto = mock(CourseDto.class);

        when(courseFacade.get(courseId)).thenReturn(courseDto);

        // when
        CourseDto result = testInstance.getCourse(courseId);

        // then
        assertThat(result).isEqualTo(courseDto);
    }

    @Test
    void shouldReturnReplacedCourseDto() {
        // given
        long courseId = 62L;
        CourseDto updatedCourse = mock(CourseDto.class);

        when(courseFacade.replace(updatedCourse, courseId)).thenReturn(updatedCourse);

        // when
        CourseDto result = testInstance.replaceCourse(updatedCourse, courseId);

        // then
        assertThat(result).isEqualTo(updatedCourse);
    }

    @Test
    void shouldCallFacadeToDeleteById() {
        // given
        long courseId = 54L;

        // when
        testInstance.deleteCourse(courseId);

        // then
        verify(courseFacade).delete(courseId);
    }

    @Test
    void shouldCallFacadeDeleteAll() {
        // when
        testInstance.deleteCourses();

        // then
        verify(courseFacade).deleteAll();
    }

    private List<CourseDto> createCoursesDto() {
        CourseDto courseDto1 = mock(CourseDto.class);
        CourseDto courseDto2 = mock(CourseDto.class);

        List<CourseDto> coursesDto = new ArrayList<>();
        coursesDto.add(courseDto1);
        coursesDto.add(courseDto2);

        return coursesDto;
    }
}