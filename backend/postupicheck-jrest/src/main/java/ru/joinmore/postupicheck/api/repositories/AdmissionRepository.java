package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Boolean existsByCourseNameAndStudent(String name, Student student);

    List<Admission> findAdmissionsByStudent(Student student);

    List<Admission> findAdmissionsByStudentId(long id);

    @Query("select a from Admission a where a.student = ?1 and a.course.university = ?2")
    List<Admission> findAdmissionsByStudentAndUniversity(Student student, University university);

    List<Admission> findAdmissionsByCourseOrderByPoints(Course course);

    Admission findAdmissionByStudentAndCourse(Student student, Course course);
}
