package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.entities.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
}
