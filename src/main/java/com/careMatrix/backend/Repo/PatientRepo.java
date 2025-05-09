package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByNameAndAge(String name, int age);
    
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) = LOWER(:name) AND p.dateOfBirth = :dateOfBirth")
    List<Patient> findByNameAndDateOfBirth(@Param("name") String name, @Param("dateOfBirth") LocalDate dateOfBirth);

    Optional<Patient> findByUserId(int userId);

    Patient findByUser(Users user);
}