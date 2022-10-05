package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.CourseRequiredSubject;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.List;

@Repository
public interface CourseRequiredSubjectRepository extends JpaRepository<CourseRequiredSubject, Long> {
    @Query("select c from CourseRequiredSubject c where c.course = ?1")
    List<CourseRequiredSubject> findCourseRequiredSubjectsByCourse(Course course);
}
