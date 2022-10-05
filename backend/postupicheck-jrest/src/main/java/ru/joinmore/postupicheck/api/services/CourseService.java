package ru.joinmore.postupicheck.api.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.exceptions.AlreadyExistsException;
import ru.joinmore.postupicheck.api.exceptions.ResourceNotExistsException;
import ru.joinmore.postupicheck.api.repositories.CourseRepository;
import ru.joinmore.postupicheck.api.repositories.CourseRequiredSubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseRequiredSubjectRepository courseRequiredSubjectRepository;

    public CourseService(CourseRepository repository,
                         CourseRequiredSubjectRepository courseRequiredSubjectRepository) {
        this.repository = repository;
        this.courseRequiredSubjectRepository = courseRequiredSubjectRepository;
    }

    public List<Course> getAll() {
        return repository.findAll();
    }

    public Course get(long id) {
        return repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Course with id [" + id + "]"));
    }

    public Course create(Course course) {
        String name = course.getName();
        Boolean exists = repository.existsByName(name);

        if (exists) {
            University university = course.getUniversity();
            String message = String.format(
                    "Course with name %s in university + %d ",
                    name,
                    university.getId());
            throw new AlreadyExistsException(message);
        }

        return repository.save(course);
    }

    public Course replace(Course updatedCourse, long id) {
        Course course = repository
                .findById(id) //
                .orElseThrow(() -> new ResourceNotExistsException("Course with id [" + id + "]"));

        return replaceCourse(course, updatedCourse);
    }

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExistsException("Course with id [" + id + "]");
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Course> findCoursesByUniversity(University university) {
        return repository.findCoursesByUniversity(university);
    }

    public List<CourseRequiredSubject> getRequiredSubjects(Course course) {
        return courseRequiredSubjectRepository.findCourseRequiredSubjectsByCourse(course);
    }

    public List<Course> saveAll(List<Course> courses) {
        return repository.saveAll(courses);

    }

    private Course replaceCourse(Course course, Course updatedCourse) {
        course.setName(updatedCourse.getName());
        course.setCode(updatedCourse.getCode());
        course.setUniversity(updatedCourse.getUniversity());
        List<Subject> updatedRequiredSubjects = updatedCourse.getRequiredSubjects();
        List<CourseRequiredSubject> courseRequiredSubjects = courseRequiredSubjectRepository
                .findCourseRequiredSubjectsByCourse(course);

        course.setCurPassingPoints(updatedCourse.getCurPassingPoints());
        course.setBudgetPlaces(updatedCourse.getBudgetPlaces());

        replaceRequiredSubjects(courseRequiredSubjects, updatedRequiredSubjects, course);

        return repository.save(course);
    }

    public void createAndSaveRequiredSubjects(List<Subject> requiredSubjects, Course course) {
        List<CourseRequiredSubject> courseRequiredSubjects = new ArrayList<>();
        requiredSubjects.forEach(subject -> {
            CourseRequiredSubject courseRequiredSubject = new CourseRequiredSubject(course, subject);
            courseRequiredSubjects.add(courseRequiredSubject);
        });

        courseRequiredSubjectRepository.saveAll(courseRequiredSubjects);
    }

    private void replaceRequiredSubjects(
            List<CourseRequiredSubject> courseRequiredSubjects,
            List<Subject> updatedRequiredSubjects,
            Course course) {
        List<Long> oldRequiredSubjectIds = courseRequiredSubjects.stream()
                .map(CourseRequiredSubject::getId).toList();
        courseRequiredSubjectRepository.deleteAllById(oldRequiredSubjectIds);
        createAndSaveRequiredSubjects(updatedRequiredSubjects, course);
    }
}
