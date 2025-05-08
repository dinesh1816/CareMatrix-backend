package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Allergy;
import com.careMatrix.backend.Entity.SurgicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SurgicalHistoryRepo extends JpaRepository<SurgicalHistory, Long> {
    Page<SurgicalHistory> findAllByPatientId(Long patientId, Pageable pageable);
}
