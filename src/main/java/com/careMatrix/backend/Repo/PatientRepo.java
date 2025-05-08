package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByNameAndAge(String name, int age);

    Optional<Patient> findByUserId(int userId);

    Patient findByUser(Users user);
}