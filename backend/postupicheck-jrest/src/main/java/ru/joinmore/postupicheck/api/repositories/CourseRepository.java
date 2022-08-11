package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Subject;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Boolean existsByName(String name);

    List<Course> findCoursesByUniversity(University university);

    List<Course> findCoursesByUniversityAndThirdSubject(University university, Subject subject);
}
