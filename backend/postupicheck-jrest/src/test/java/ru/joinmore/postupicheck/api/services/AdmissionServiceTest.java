package ru.joinmore.postupicheck.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.AdmissionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdmissionServiceTest {

    @Mock
    private AdmissionRepository admissionRepository;
    private AdmissionService testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new AdmissionService(admissionRepository);
    }

    @Test
    void shouldCallRepositoryFindAll() {
        // when
        testInstance.getAll();

        // then
        verify(admissionRepository).findAll();
    }

    @Test
    void shouldReturnAllAdmissions_WhenRepositoryFindAll() {
        // given
        List<Admission> allAdmissions = createAdmissionList();
        Admission admission1 = allAdmissions.get(0);
        Admission admission2 = allAdmissions.get(1);
        Admission admission3 = allAdmissions.get(2);

        when(admissionRepository.findAll()).thenReturn(allAdmissions);

        // when
        List<Admission> result = testInstance.getAll();

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldFindAdmissionById_WhenAdmissionExists() {
        // given
        long id = 123L;
        var testAdmission = Optional.of(mock(Admission.class));

        when(admissionRepository.findById(id)).thenReturn(testAdmission);

        // when
        testInstance.get(id);

        // then
        verify(admissionRepository).findById(id);
    }

    @Test
    void shouldThrowResourceNotExistException_WhenFindByIdDoesntExist() {
        // given
        long id = 123L;

        // when
        when(admissionRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() ->
                testInstance.get(id)
        )
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Admission with id [123]");
    }

    @Test
    void shouldReturnExistingAdmission_WhenFindById() {
        // given
        long id = 123L;
        Admission testAdmission = mock(Admission.class);

        when(admissionRepository.findById(id)).thenReturn(Optional.of(testAdmission));

        // when
        Admission result = testInstance.get(id);

        // then
        assertThat(result).isEqualTo(testAdmission);
    }

    @Test
    void shouldSaveAdmissionIfDoesntExist() {
        // given
        Student student = mock(Student.class);
        Course course = mock(Course.class);

        int points = 235;
        Admission admission = new Admission(student, course, points);

        // when
        testInstance.create(admission);

        // then
        verify(admissionRepository).save(admission);
    }

    @Test
    void shouldReturnSavedAdmission_WhenCreate() {
        // given
        Student student = mock(Student.class);
        Course course = mock(Course.class);

        int points = 234;
        Admission admission = new Admission(student, course, points);

        when(admissionRepository.save(admission)).thenReturn(admission);

        // when
        Admission result = testInstance.create(admission);

        // then
        assertThat(result).isEqualTo(admission);
    }

    @Test
    void ShouldNotSaveAdmission_WhenAdmissionExist() {
        // given
        String name = "testCourseName";
        Student student = new Student("testName", "testSnils");
        Course course = new Course();
        course.setName(name);

        int points = 123;
        Admission admission = new Admission(student, course, points);

        // when
        when(admissionRepository.existsByCourseNameAndStudent(name, student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(admission));
        verify(admissionRepository, never()).save(admission);
    }

    @Test
    void ShouldThrowAlreadyExistsException_WhenAdmissionExist_WhenCreate() {
        // given
        String name = "testCourseName";
        Student student = new Student("testName", "testSnils");
        Course course = new Course();
        course.setName(name);

        int points = 143;
        Admission admission = new Admission(student, course, points);

        // when
        when(admissionRepository.existsByCourseNameAndStudent(name, student)).thenReturn(true);

        // then
        assertThatThrownBy(() -> testInstance.create(admission))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining(admission.getStudent().getName())
                .hasMessageContaining(admission.getCourse().getName());
    }

    @Test
    void shouldReplaceOldAdmissionByNewAdmission_WhenOldAdmissionExists() {
        // given
        Student newStudent = mock(Student.class);
        Course newCourse = mock(Course.class);
        Admission oldAdmission = mock(Admission.class);

        boolean newConsent = true;
        int points = 235;
        Admission newAdmission = new Admission(newStudent, newCourse, newConsent, points);
        long id = 45L;

        when(admissionRepository.findById(id)).thenReturn(Optional.of(oldAdmission));

        // when
        testInstance.replace(newAdmission, id);

        // then
        InOrder inOrder = inOrder(oldAdmission, admissionRepository);
        inOrder.verify(oldAdmission).setStudent(newStudent);
        inOrder.verify(oldAdmission).setCourse(newCourse);
        inOrder.verify(oldAdmission).setConsent(newConsent);
        inOrder.verify(oldAdmission).setPoints(points);
        inOrder.verify(admissionRepository).save(oldAdmission);
    }

    @Test
    void shouldReturnReplacedAdmission_WhenReplace() {
        // given
        Student newStudent = mock(Student.class);
        Course newCourse = mock(Course.class);
        Admission oldAdmission = mock(Admission.class);

        int points = 124;
        Admission newAdmission = new Admission(newStudent, newCourse, points);
        long id = 45L;

        when(admissionRepository.findById(id)).thenReturn(Optional.of(oldAdmission));
        when(admissionRepository.save(oldAdmission)).thenReturn(oldAdmission);

        // when
        Admission result = testInstance.replace(newAdmission, id);

        // then
        assertThat(result).isEqualTo(oldAdmission);
    }

    @Test
    void shouldNotReplaceAdmission_WhenDoesntExists() {
        // given
        long id = 123L;
        var oldAdmission = mock(Admission.class);

        when(admissionRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() ->
                testInstance.replace(new Admission(), id)
        );

        // then
        verify(admissionRepository, never()).save(oldAdmission);
    }

    @Test
    void shouldThrowResourceNotExistsException_WhenDoesntExistsReplacement() {
        // given
        long id = 123L;

        // when
        when(admissionRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() ->
                testInstance.replace(new Admission(), id)
        )
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Admission with id [123]");
    }

    @Test
    void shouldCallRepositoryDeleteById() {
        // given
        long id = 234L;

        // when
        testInstance.delete(id);

        // then
        verify(admissionRepository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotExistsException_WhenDoesntExistsDeletion() {
        // given
        long id = 234L;

        // when
        doThrow(new EmptyResultDataAccessException(1)).when(admissionRepository).deleteById(id);

        // then
        assertThatThrownBy(() -> testInstance.delete(id))
                .isInstanceOf(ResourceNotExistsException.class)
                .hasMessageContaining("Admission with id [" + id + "]");
    }

    @Test
    void shouldCallRepositoryFindAdmissionsByStudent() {
        // given
        Student student = mock(Student.class);

        // when
        testInstance.findAdmissionsByStudent(student);

        // then
        verify(admissionRepository).findAdmissionsByStudent(student);
    }

    @Test
    void shouldReturnAdmissions_WhenFindAdmissionsByStudent() {
        // given
        Student student = mock(Student.class);

        List<Admission> studentAdmissions = createAdmissionList();
        Admission admission1 = studentAdmissions.get(0);
        Admission admission2 = studentAdmissions.get(1);
        Admission admission3 = studentAdmissions.get(2);

        when(admissionRepository.findAdmissionsByStudent(student)).thenReturn(studentAdmissions);

        // when
        List<Admission> result = testInstance.findAdmissionsByStudent(student);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldCallRepositoryFindAdmissionsByStudentAndCourseUniversity() {
        // given
        Student student = mock(Student.class);
        University university = mock(University.class);

        // when
        testInstance.findAdmissionsByStudentAndCourseUniversity(student, university);

        // then
        verify(admissionRepository).findAdmissionsByStudentAndCourseUniversity(student, university);
    }

    @Test
    void shouldReturnAdmissions_WhenFindAdmissionsByStudentAndCourseUniversity() {
        // given
        Student student = mock(Student.class);
        University university = mock(University.class);

        List<Admission> studentAdmissions = createAdmissionList();
        Admission admission1 = studentAdmissions.get(0);
        Admission admission2 = studentAdmissions.get(1);
        Admission admission3 = studentAdmissions.get(2);

        when(admissionRepository
                .findAdmissionsByStudentAndCourseUniversity(student, university))
                .thenReturn(studentAdmissions);

        // when
        List<Admission> result = testInstance.findAdmissionsByStudentAndCourseUniversity(student, university);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldCallRepositoryDeleteAll() {
        // when
        testInstance.deleteAll();

        // then
        verify(admissionRepository).deleteAll();
    }

    @Test
    void shouldCallRepositoryFindAdmissionsByStudentId() {
        // given
        long id = 5;

        // when
        testInstance.findAdmissionsByStudentId(id);

        // then
        verify(admissionRepository).findAdmissionsByStudentId(id);
    }

    @Test
    void shouldReturnStudentAdmissions_WhenFindAdmissionsById() {
        // given
        long id = 5;
        List<Admission> studentAdmissions = createAdmissionList();
        Admission admission1 = studentAdmissions.get(0);
        Admission admission2 = studentAdmissions.get(1);
        Admission admission3 = studentAdmissions.get(2);
        when(testInstance.findAdmissionsByStudentId(id)).thenReturn(studentAdmissions);

        // when
        List<Admission> result = testInstance.findAdmissionsByStudentId(id);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldCallRepositoryGetCourseAdmissions() {
        // given
        Course course = mock(Course.class);

        // when
        testInstance.getCourseAdmissions(course);

        // then
        verify(admissionRepository).findAdmissionsByCourseOrderByPoints(course);
    }

    @Test
    void shouldReturnCourseAdmissions() {
        // given
        List<Admission> courseAdmissions = createAdmissionList();
        Admission admission1 = courseAdmissions.get(0);
        Admission admission2 = courseAdmissions.get(1);
        Admission admission3 = courseAdmissions.get(2);
        Course course = mock(Course.class);

        when(testInstance.getCourseAdmissions(course)).thenReturn(courseAdmissions);

        // when
        List<Admission> result = testInstance.getCourseAdmissions(course);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }

    @Test
    void shouldCallRepositorySaveALl() {
        // given
        List<Admission> allAdmissions = createAdmissionList();

        // when
        testInstance.saveAll(allAdmissions);

        // then
        verify(admissionRepository).saveAll(allAdmissions);
    }

    @Test
    void shouldReturnAllAdmissions_WhenRepositorySaveAll() {
        // given
        List<Admission> allAdmissions = createAdmissionList();
        Admission admission1 = allAdmissions.get(0);
        Admission admission2 = allAdmissions.get(1);
        Admission admission3 = allAdmissions.get(2);

        when(admissionRepository.saveAll(allAdmissions)).thenReturn(allAdmissions);

        // when
        List<Admission> result = testInstance.saveAll(allAdmissions);

        // then
        assertThat(result).contains(admission1, admission2, admission3);
    }


    @Test
    void shouldCallRepository_For_GetAdmission() {
        // given
        Course course = mock(Course.class);
        Student student = mock(Student.class);

        // when
        testInstance.getStudentAdmission(course, student);

        // then
        verify(admissionRepository).findAdmissionByStudentAndCourse(student, course);
    }
    
    private List<Admission> createAdmissionList() {
        List<Admission> admissions = new ArrayList<>();
        Admission admission1 = mock(Admission.class);
        Admission admission2 = mock(Admission.class);
        Admission admission3 = mock(Admission.class);
        admissions.add(admission1);
        admissions.add(admission2);
        admissions.add(admission3);

        return admissions;
    }
}