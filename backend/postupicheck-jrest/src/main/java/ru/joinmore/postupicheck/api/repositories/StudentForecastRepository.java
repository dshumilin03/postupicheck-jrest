package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Student;
import ru.joinmore.postupicheck.api.entities.StudentForecast;

@Repository
public interface StudentForecastRepository extends JpaRepository<StudentForecast, Long> {

    Boolean existsStudentForecastByAdmission_Student(Student student);

    StudentForecast getStudentForecastByAdmission_Student_Id(long id);
}
