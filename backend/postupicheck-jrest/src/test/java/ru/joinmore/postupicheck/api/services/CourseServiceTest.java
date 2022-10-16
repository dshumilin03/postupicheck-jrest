package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.converters.CourseRequiredSubjectsReverseConverter;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.CourseRepository;
import ru.joinmore.postupicheck.api.repositories.CourseRequiredSubjectRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseRequiredSubjectRepository courseRequiredSubjectRepository;
    @Mock
    private CourseRequiredSubjectsReverseConverter courseRequiredSubjectsReverseConverter;
    private CourseService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new CourseService(
                courseRepository,
                courseRequiredSubjectRepository,
                courseRequiredSubjectsReverseConverter);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(courseRepository).findAll();
    }

    @Test
    void shouldReturnAllCourses_WhenRepositoryFindAll() {
        // given
        List<Course> allCourses = createCourseList();
        Course course1 = allCourses.get(0);
        Course course2 = allCourses.get(1);
        Course course3 = allCourses.get(2);

        when(courseRepository.findAll()).thenReturn(allCourses);

        // when
        List<Course> result = testInstance.getAll();

        // then
        assertThat(result).contains(course1, course2, course3);
    }

    @Test
    void shouldCallRepositoryFindCourseById() {
        // given
        long id = 34L;
        var testCourse = Optional.of(mock(Course.class));

        when(courseRepository.findById(id)).thenReturn(testCourse);

        // when
        testInstance.get(id);

        // then
        verify(courseRepository).findById(id);
    }

    @Test
    void shouldThrowResourceNotFoundException_WhenFindByByIdDoesntExists() {
        // given
        long id = 34L;

        // when
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() ->
                testInstance.get(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Course with id [34]");
    }

    @Test
    void shouldReturnCourse_WhenFindById() {
        // given
        long id = 34L;
        Course course = mock(Course.class);

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // when
        Course result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(course);
    }

    @Test
    void shouldSaveCourseIfDoesntExists() {
        // given
        Course course = mock(Course.class);

        // when
        testInstance.create(course);

        // then
        verify(courseRepository).save(course);
    }

    @Test
    void shouldReturnCreatedCourse() {
        // given
        Course course = mock(Course.class);

        when(courseRepository.save(course)).thenReturn(course);

        // when
        Course result = testInstance.create(course);

        // then
        assertThat(result).isEqualTo(course);
    }

    @Test
    void shouldNotSaveCourse_WhenExists() {
        // given
        Course course = mock(Course.class);
        String name = ("testName");
        course.setName(name);

        // when
        when(courseRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(course));
        verify(courseRepository, never()).save(course);
    }

    @Test
    void shouldThrowAlreadyExistsException_WhenExists() {
        // given
        University university = new University();
        university.setId(14L);

        Course course = new Course();
        course.setUniversity(university);
        String name = ("testName");
        course.setName(name);

        // when
        when(courseRepository.existsByName(name)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(course))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("Course with name %s in university + %d ", name, university.getId());
    }

    // TODO rework with subject Ids
//    @Test
//    void shouldCallReplaceMethodsForCourseInOrder() {
//        // given
//        String newName = "newName";
//        String newCode = "newName";
//
//        Course oldCourse = mock(Course.class);
//        University newUniversity = mock(University.class);
//        Subject subject1 = mock(Subject.class);
//        Subject subject2 = mock(Subject.class);
//        Subject subject3 = mock(Subject.class);
//
//        List<Long> subjectIds = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
//        List<CourseRequiredSubject> courseRequiredSubjects =
//                createCourseRequiredSubjects(oldCourse, subject1, subject2, subject3);
//
//        int curPassingPoints = 200;
//        int budgetPlaces = 31;
//        Course newCourse = new Course(
//                newName,
//                newCode,
//                newUniversity,
//                curPassingPoints,
//                budgetPlaces);
//        long id = 234L;
//        newCourse.setRequiredSubjects(courseRequiredSubjects);
//
//        when(courseRepository.findById(id)).thenReturn(Optional.of(oldCourse));
//        when(courseRequiredSubjectRepository.findCourseRequiredSubjectsByCourse(oldCourse))
//                .thenReturn(courseRequiredSubjects);
//
//        // when
//        testInstance.replace(newCourse, subjectIds, id);
//
//        // then
//        InOrder inOrder = inOrder(oldCourse, courseRepository);
//        inOrder.verify(oldCourse).setName(newName);
//        inOrder.verify(oldCourse).setCode(newCode);
//        inOrder.verify(oldCourse).setUniversity(newUniversity);
//
//        inOrder.verify(oldCourse).setCurPassingPoints(curPassingPoints);
//        inOrder.verify(oldCourse).setBudgetPlaces(budgetPlaces);
//        inOrder.verify(courseRepository).save(oldCourse);
//    }

// TODO rewrite

//    @Test
//    void shouldReturnReplacedCourse_WhenReplace() {
//        // given
//        String newName = "newName";
//        String newCode = "newName";
//
//        Course oldCourse = mock(Course.class);
//        University newUniversity = mock(University.class);
//        Subject subject1 = mock(Subject.class);
//        Subject subject2 = mock(Subject.class);
//        Subject subject3 = mock(Subject.class);
//        List<CourseRequiredSubject> requiredSubjects =
//                createCourseRequiredSubjects(oldCourse, subject1, subject2, subject3);
//
//        int curPassingPoints = 200;
//        int budgetPlaces = 55;
//        Course newCourse = new Course(
//                newName,
//                newCode,
//                newUniversity,
//                curPassingPoints,
//                budgetPlaces);
//        long id = 234L;
//        newCourse.setRequiredSubjects(requiredSubjects);
//
//        when(courseRepository.findById(id)).thenReturn(Optional.of(oldCourse));
//        when(courseRepository.save(oldCourse)).thenReturn(oldCourse);
//
//        // when
//        Course result = testInstance.replace(newCourse, id);
//
//        // then
//        assertThat(result).isEqualTo(oldCourse);
//    }

//    @Test
//    void shouldNotReplaceCourse_WhenDoesntExists() {
//        // given
//        Course oldCourse = mock(Course.class);
//        long id = 234L;
//
//        when(courseRepository.findById(id)).thenReturn(Optional.empty());
//
//        // when
//        assertThatThrownBy(() -> testInstance.replace(new Course(), id));
//
//        // then
//        verify(courseRepository, never()).save(oldCourse);
//    }

//    @Test
//    void shouldThrowResourceNotExistsException_WhenDoesntExistsReplacement() {
//        // given
//        long id = 234L;
//
//        // when
//        when(courseRepository.findById(id)).thenReturn(Optional.empty());
//
//        // then
//        assertThatThrownBy(() -> testInstance.replace(new Course(), id))
//                .isInstanceOf(ResourceNotExistsException.class)
//                .hasMessageContaining("Course with id [234]");
//    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 23L;

        // when
        testInstance.delete(id);

        // then
        verify(courseRepository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotExistsException_WhenDoesntExistsDeletion() {
        // given
        long id = 23L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(courseRepository).deleteById(id);

        // then
        assertThatThrownBy(() ->
                testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Course with id [23]");
    }

    @Test
    void shouldCallRepositoryDeleteAll() {
        // when
        testInstance.deleteAll();

        // then
        verify(courseRepository).deleteAll();
    }

    @Test
    void shouldCallRepositoryFindCoursesByUniversity() {
        // given
        University university = mock(University.class);

        // when
        testInstance.findCoursesByUniversity(university);

        // then
        verify(courseRepository).findCoursesByUniversity(university);
    }

    @Test
    void shouldReturnCourses_WhenFindCoursesByUniversity() {
        // given
        University university = mock(University.class);

        List<Course> universityCourses = createCourseList();
        Course course1 = universityCourses.get(0);
        Course course2 = universityCourses.get(1);
        Course course3 = universityCourses.get(2);

        when(courseRepository.findCoursesByUniversity(university)).thenReturn(universityCourses);

        // when
        List<Course> result = testInstance.findCoursesByUniversity(university);

        // then
        assertThat(result).contains(course1, course2, course3);
    }

    @Test
    void shouldCallCourseRequiredSubjectsRepository_WhenGetRequiredSubjects() {
        // given
        Course course = mock(Course.class);

        // when
        testInstance.getRequiredSubjects(course);

        // then
        verify(courseRequiredSubjectRepository).findCourseRequiredSubjectsByCourse(course);
    }

    @Test
    void shouldReturnCourseRequiredSubjects() {
        // given
        Subject subject1 = mock(Subject.class);
        Subject subject2 = mock(Subject.class);
        Subject subject3 = mock(Subject.class);

        Course course = new Course();
        List<CourseRequiredSubject> courseRequiredSubjects =
                createCourseRequiredSubjects(course, subject1, subject2, subject3);

        when(courseRequiredSubjectRepository.findCourseRequiredSubjectsByCourse(course))
                .thenReturn(courseRequiredSubjects);

        // when
        List<CourseRequiredSubject> result = testInstance.getRequiredSubjects(course);

        // then
        CourseRequiredSubject requiredSubject1 = courseRequiredSubjects.get(0);
        CourseRequiredSubject requiredSubject2 = courseRequiredSubjects.get(1);
        CourseRequiredSubject requiredSubject3 = courseRequiredSubjects.get(2);
        assertThat(result).contains(requiredSubject1, requiredSubject2, requiredSubject3);
    }

    @Test
    void shouldCallRepositorySaveALl() {
        // given
        List<Course> allCourses = createCourseList();

        // when
        testInstance.saveAll(allCourses);

        // then
        verify(courseRepository).saveAll(allCourses);
    }

    @Test
    void shouldReturnAllCourses_WhenRepositorySaveAll() {
        // given
        List<Course> allCourses = createCourseList();
        Course course1 = allCourses.get(0);
        Course course2 = allCourses.get(1);
        Course course3 = allCourses.get(2);

        when(courseRepository.saveAll(allCourses)).thenReturn(allCourses);

        // when
        List<Course> result = testInstance.saveAll(allCourses);

        // then
        assertThat(result).contains(course1, course2, course3);
    }

    private List<Course> createCourseList() {
        Course course1 = mock(Course.class);
        Course course2 = mock(Course.class);
        Course course3 = mock(Course.class);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        return courses;
    }

    private List<CourseRequiredSubject> createCourseRequiredSubjects(
            Course course,
            Subject subject1,
            Subject subject2,
            Subject subject3) {
        CourseRequiredSubject courseRequiredSubject1 = new CourseRequiredSubject(course, subject1);
        CourseRequiredSubject courseRequiredSubject2 = new CourseRequiredSubject(course, subject2);
        CourseRequiredSubject courseRequiredSubject3 = new CourseRequiredSubject(course, subject3);

        List<CourseRequiredSubject> courseRequiredSubjects = new ArrayList<>();
        courseRequiredSubjects.add(courseRequiredSubject1);
        courseRequiredSubjects.add(courseRequiredSubject2);
        courseRequiredSubjects.add(courseRequiredSubject3);

        return courseRequiredSubjects;
    }
}