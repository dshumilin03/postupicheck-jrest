package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.models.StudentExamResults;

@Repository
public interface StudentExamResultsRepository extends JpaRepository<StudentExamResults, Long> {
}
