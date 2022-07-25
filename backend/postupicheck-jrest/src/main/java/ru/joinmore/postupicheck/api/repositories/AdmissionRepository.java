package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Admission;
import ru.joinmore.postupicheck.api.entities.Course;
import ru.joinmore.postupicheck.api.entities.Student;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Boolean existsByCourseAndStudent(Course course, Student student);

    List<Admission> findAdmissionsByStudent(Student student);
}
