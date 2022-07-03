package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
