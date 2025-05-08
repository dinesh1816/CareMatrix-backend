package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    Doctor findByUser(Users user);

    Optional<Doctor> findByUserId(int userId);
}
