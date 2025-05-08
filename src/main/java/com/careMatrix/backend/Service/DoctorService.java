package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Repo.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Doctor updateDoctor(Long doctorId, Doctor updatedDoctor) {
        Doctor existing = getDoctorById(doctorId);
        existing.setUniqueId(updatedDoctor.getUniqueId());
        existing.setName(updatedDoctor.getName());
        existing.setEmail(updatedDoctor.getEmail());
        existing.setAge(updatedDoctor.getAge());
        existing.setDateOfBirth(updatedDoctor.getDateOfBirth());
        existing.setGender(updatedDoctor.getGender());
        existing.setPhone(updatedDoctor.getPhone());

        return doctorRepo.save(existing);
    }

    public void deleteDoctor(Long doctorId) {
        Doctor existing = getDoctorById(doctorId);
        doctorRepo.delete(existing);
    }
}
