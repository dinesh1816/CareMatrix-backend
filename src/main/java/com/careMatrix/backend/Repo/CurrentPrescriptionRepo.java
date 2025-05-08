package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.CurrentPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CurrentPrescriptionRepo extends JpaRepository<CurrentPrescription,Long> {
    Page<CurrentPrescription> findAllByPatientId(Long patientId, Pageable pageable);
}
