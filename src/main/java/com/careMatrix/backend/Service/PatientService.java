package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepo patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> getByNameAndAge(String name, int age){
        return patientRepository.findByNameAndAge(name, age);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long patientId, Patient updatedPatient) {
        Patient existingPatient = getPatientById(patientId);
        // Update fields as necessary
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setAge(updatedPatient.getAge());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setBloodGroup(updatedPatient.getBloodGroup());
        // Add any additional fields here
        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long patientId) {
        Patient existingPatient = getPatientById(patientId);
        patientRepository.delete(existingPatient);
    }

    public List<Patient> searchByName(String name) {
        return patientRepository.findByNameContainingIgnoreCase(name);
    }
}
