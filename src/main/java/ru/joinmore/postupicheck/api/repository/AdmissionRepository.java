package ru.joinmore.postupicheck.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.joinmore.postupicheck.api.model.Admission;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
