package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.StudentExamResults;
import ru.joinmore.postupicheck.api.entities.Subject;

@Repository
public interface StudentExamResultsRepository extends JpaRepository<StudentExamResults, Long> {
    Boolean existsBySubject(Subject subject);
}
