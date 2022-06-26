package ru.joinmore.postupicheck.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.models.Admission;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
