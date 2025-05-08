package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepo extends JpaRepository<Consultation, Long> {
}
