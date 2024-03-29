package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Boolean existsStudentBySnils(String snils);
}
