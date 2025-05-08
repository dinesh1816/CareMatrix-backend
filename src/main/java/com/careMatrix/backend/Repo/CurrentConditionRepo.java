package com.careMatrix.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.careMatrix.backend.Entity.CurrentCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CurrentConditionRepo extends JpaRepository<CurrentCondition,Long>{
    Page<CurrentCondition> findAllByPatientId(Long patientId, Pageable pageable);
}
