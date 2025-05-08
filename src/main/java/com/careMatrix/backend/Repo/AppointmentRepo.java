package com.careMatrix.backend.Repo;

import com.careMatrix.backend.Entity.Appointment;
import com.careMatrix.backend.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorAndDateAndTime(Doctor doctor, LocalDate date, LocalTime time);

    List<Appointment> findTop2ByPatientId_IdAndDateLessThanEqualAndTimeLessThanOrderByDateDescTimeDesc(
            Long patientId, LocalDate today, LocalTime now);

    List<Appointment> findTop2ByPatientId_IdAndDateGreaterThanEqualAndTimeGreaterThanOrderByDateAscTimeAsc(
            Long patientId, LocalDate today, LocalTime now);

    List<Appointment> findTop2ByDoctor_IdAndDateLessThanEqualAndTimeLessThanOrderByDateDescTimeDesc(
            Long doctorId, LocalDate today, LocalTime now);

    List<Appointment> findTop2ByDoctor_IdAndDateGreaterThanEqualAndTimeGreaterThanOrderByDateAscTimeAsc(
            Long doctorId, LocalDate today, LocalTime now);

    Page<Appointment> findByPatient_Id(Long patientId, Pageable pageable);

    Page<Appointment> findByDoctor_Id(Long doctorId, Pageable pageable);

    List<Appointment> findByDoctor_IdAndDate(Long doctorId, LocalDate date);

}
