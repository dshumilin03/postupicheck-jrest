package ru.joinmore.postupicheck.api.facades;

import org.springframework.stereotype.Component;
import ru.joinmore.postupicheck.api.dto.CourseDto;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;
import ru.joinmore.postupicheck.api.services.CourseService;
import ru.joinmore.postupicheck.api.services.SubjectService;
import ru.joinmore.postupicheck.api.services.UniversityService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseFacade {

    private final CourseService courseService;
    private final SubjectService subjectService;
    private final UniversityService universityService;

    public CourseFacade(CourseService courseService, UniversityService universityService,SubjectService subjectService) {
        this.courseService = courseService;
        this.universityService = universityService;
        this.subjectService = subjectService;
    }

    public CourseDto get(long id) {

        Course course = courseService.get(id);

        return setCourseDto(course);
    }

    public List<CourseDto> getAll() {

        List<Course> courseList = courseService.getAll();
        List<CourseDto> courseDtoList = new ArrayList<>();

        for (Course course : courseList) {
            CourseDto courseDto = setCourseDto(course);
            courseDtoList.add(courseDto);
        }

        return courseDtoList;
    }

    public CourseDto create(CourseDto newCourseDto) {

        Course newCourse = setCourse(newCourseDto);
        Course createdCourse = courseService.create(newCourse);

        newCourseDto.setId(createdCourse.getId());

        return  newCourseDto;
    }

    public CourseDto replace(CourseDto updatedCourseDto, long id) {

        Course updatedCourse = setCourse(updatedCourseDto);
        Course newCourse = courseService.replace(updatedCourse, id);
        updatedCourseDto.setId(newCourse.getId());

        return updatedCourseDto;
    }

    public void delete(long id) {
        courseService.delete(id);
    }
    private CourseDto setCourseDto(Course course) {

        long universityId = course.getUniversity().getId();
        long firstSubjectId = course.getFirstSubject().getId();
        long secondSubjectId = course.getSecondSubject().getId();
        long thirdSubjectId = course.getThirdSubject().getId();
        long id = course.getId();
        String name = course.getName();
        String code = course.getCode();

        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName(name);
        courseDto.setCode(code);
        courseDto.setUniversityId(universityId);
        courseDto.setFirstSubjectId(firstSubjectId);
        courseDto.setSecondSubjectId(secondSubjectId);
        courseDto.setThirdSubjectId(thirdSubjectId);

        return courseDto;
    }

    private Course setCourse(CourseDto courseDto) {

        String courseName = courseDto.getName();
        String courseCode = courseDto.getCode();

        long universityId = courseDto.getUniversityId();
        long firstSubjectId = courseDto.getFirstSubjectId();
        long secondSubjectId = courseDto.getSecondSubjectId();
        long thirdSubjectId = courseDto.getThirdSubjectId();

        University university = universityService.get(universityId);
        Subject firstSubject = subjectService.get(firstSubjectId);
        Subject secondSubject = subjectService.get(secondSubjectId);
        Subject thirdSubject = subjectService.get(thirdSubjectId);


        return new Course(courseName, courseCode, university, firstSubject, secondSubject, thirdSubject);
    }

}
