package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.University;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Boolean existsByCourse_NameAndStudent(String name, Student student);
    List<Admission> findAdmissionsByStudent(Student student);
    List<Admission> findAdmissionsByStudentAndCourse_University(Student student, University university);
}
