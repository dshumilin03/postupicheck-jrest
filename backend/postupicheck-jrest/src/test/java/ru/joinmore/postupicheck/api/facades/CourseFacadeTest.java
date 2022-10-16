package ru.joinmore.postupicheck.api.facades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.Factories.CourseFactory;
import ru.joinmore.postupicheck.api.converters.CourseConverter;
import ru.joinmore.postupicheck.api.converters.CourseReverseConverter;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.services.CourseService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseFacadeTest {

    private CourseFacade testInstance;
    @Mock
    private CourseService courseService;
    @Mock
    private CourseFactory courseFactory;
    @Mock
    private CourseConverter converter;
    @Mock
    private CourseReverseConverter reverseConverter;

    @BeforeEach
    void setUp() {
        testInstance = new CourseFacade(courseService, converter, reverseConverter, courseFactory);
    }

    @Test
    void shouldCallCourseServiceAndConverter_WhenGet() {
        // given
        long id = 5L;
        Course course = mock(Course.class);
        when(courseService.get(id)).thenReturn(course);

        // when
        testInstance.get(id);

        // then
        verify(converter).convert(course);
    }

    @Test
    void shouldReturnConvertedCourse_WhenGet() {
        // given
        long id = 5L;
        Course course = mock(Course.class);
        CourseDto convertedCourse = mock(CourseDto.class);

        when(courseService.get(id)).thenReturn(course);
        when(converter.convert(course)).thenReturn(convertedCourse);

        // when
        CourseDto result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(convertedCourse);
    }

    @Test
    void shouldCallConvertListAndCourseService_WhenGetAll() {
        // given
        List<Course> courseList = new ArrayList<>();

        // when
        testInstance.getAll();

        //then
        verify(courseService).getAll();
        verify(converter).convert(courseList);
    }

    @Test
    void shouldReturnConvertedList_WhenGetAll() {
        // given
        List<Course> courseList = new ArrayList<>();

        CourseDto courseDto1 = mock(CourseDto.class);
        CourseDto courseDto2 = mock(CourseDto.class);
        CourseDto courseDto3 = mock(CourseDto.class);

        List<CourseDto> convertedList = new ArrayList<>();
        convertedList.add(courseDto1);
        convertedList.add(courseDto2);
        convertedList.add(courseDto3);

        when(courseService.getAll()).thenReturn(courseList);
        when(converter.convert(courseList)).thenReturn(convertedList);

        // when
        List<CourseDto> result = testInstance.getAll();

        //then
        assertThat(result).contains(courseDto1, courseDto2, courseDto3);
    }

    @Test
    void shouldCallReverseConverterAndCourseServiceAndConverter_WhenCreate() {
        // given
        CourseDto newCourseDto = mock(CourseDto.class);
        Course newCourse = mock(Course.class);
        Course createdCourse = mock(Course.class);
        List<Long> subjectsId = new ArrayList<>();

        when(reverseConverter.convert(newCourseDto)).thenReturn(newCourse);
        when(courseFactory.create(newCourse, subjectsId)).thenReturn(createdCourse);

        // when
        testInstance.create(newCourseDto);

        // then
        verify(converter).convert(createdCourse);
    }

    @Test
    void shouldReturnConvertedCourse_WhenCreate() {
        // given
        CourseDto newCourseDto = mock(CourseDto.class);
        Course newCourse = mock(Course.class);
        Course createdCourse = mock(Course.class);
        CourseDto convertedCourse = mock(CourseDto.class);
        List<Long> subjectsId = new ArrayList<>();

        when(reverseConverter.convert(newCourseDto)).thenReturn(newCourse);
        when(courseFactory.create(newCourse, subjectsId)).thenReturn(createdCourse);
        when(converter.convert(createdCourse)).thenReturn(convertedCourse);

        // when
        CourseDto result = testInstance.create(newCourseDto);

        // then
        assertThat(result).isEqualTo(convertedCourse);
    }
// TODO rewrite

//    @Test
//    void shouldCallReverseConverterAndCourseServiceAndConverter_WhenReplace() {
//        // given
//        long id = 15L;
//        CourseDto updatedCourseDto = mock(CourseDto.class);
//        Course updatedCourse = mock(Course.class);
//        Course newCourse = mock(Course.class);
//
//        when(reverseConverter.convert(updatedCourseDto)).thenReturn(updatedCourse);
//        when(courseService.replace(updatedCourse, id)).thenReturn(newCourse);
//
//        // when
//        testInstance.replace(updatedCourseDto, id);
//
//        // then
//        verify(converter).convert(newCourse);
//    }

    // TODO rewrite

//    @Test
//    void shouldReturnConvertedCourse_WhenReplace() {
//        // given
//        long id = 515L;
//        CourseDto updatedCourseDto = mock(CourseDto.class);
//        Course updatedCourse = mock(Course.class);
//        Course newCourse = mock(Course.class);
//        CourseDto convertedCourse = mock(CourseDto.class);
//
//        when(reverseConverter.convert(updatedCourseDto)).thenReturn(updatedCourse);
//        when(courseService.replace(updatedCourse, id)).thenReturn(newCourse);
//        when(converter.convert(newCourse)).thenReturn(convertedCourse);
//
//        // when
//        CourseDto result = testInstance.replace(updatedCourseDto, id);
//
//        // then
//        assertThat(result).isEqualTo(convertedCourse);
//    }


    @Test
    void shouldCallCourseServiceDelete() {
        // given
        long id = 5L;

        // when
        testInstance.delete(id);

        // then
        verify(courseService).delete(id);
    }

    @Test
    void shouldCallCourseServiceDeleteAll() {
        // when
        testInstance.deleteAll();

        // then
        verify(courseService).deleteAll();
    }
}