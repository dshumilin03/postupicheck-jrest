package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentExamResult;
import ru.joinmore.postupicheck.api.entities.Subject;

import java.util.List;

@Repository
public interface StudentExamResultRepository extends JpaRepository<StudentExamResult, Long> {
    Boolean existsBySubjectAndStudent(Subject subject, Student student);

    List<StudentExamResult> findStudentExamResultsByStudentId(long id);
    List<StudentExamResult> findStudentExamResultsByStudent(Student student);

    @Query("select s.points from StudentExamResult s where s.student = ?1 and s.subject = ?2")
    int getPointsByStudentAndSubject(Student student, Subject subject);

}
