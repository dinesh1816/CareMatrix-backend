package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Allergy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepo extends JpaRepository<Allergy,Long>{
    Page<Allergy> findAllByPatientId(Long patientId, Pageable pageable);
}
