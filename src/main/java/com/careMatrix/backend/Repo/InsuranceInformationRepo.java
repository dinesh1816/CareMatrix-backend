package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.InsuranceInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceInformationRepo extends JpaRepository<InsuranceInformation, Long> {
    Page<InsuranceInformation> findByPatient_Id(Long patientId, Pageable pageable);
}
