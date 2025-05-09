package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public List<Patient> getByNameAndDateOfBirth(String name, LocalDate dateOfBirth) {
        System.out.println("Searching for patient with name: " + name + " and dateOfBirth: " + dateOfBirth);
        
        // First, let's see all patients in the database
        List<Patient> allPatients = patientRepository.findAll();
        System.out.println("All patients in database:");
        for (Patient p : allPatients) {
            System.out.println("Patient: " + p.getName() + ", DOB: " + p.getDateOfBirth());
        }
        
        // Try to find by name first
        List<Patient> patients = patientRepository.findByNameContainingIgnoreCase(name);
        System.out.println("Found " + patients.size() + " patients with matching name");
        
        // If we found patients by name, check their dates
        if (!patients.isEmpty()) {
            for (Patient p : patients) {
                if (p.getDateOfBirth() != null) {
                    // Extract month and day from both dates
                    int searchMonth = dateOfBirth.getMonthValue();
                    int searchDay = dateOfBirth.getDayOfMonth();
                    int patientMonth = p.getDateOfBirth().getMonthValue();
                    int patientDay = p.getDateOfBirth().getDayOfMonth();
                    
                    // Compare only month and day
                    if (searchMonth == patientMonth && searchDay == patientDay) {
                        System.out.println("Found matching patient: " + p.getName() + " with DOB: " + p.getDateOfBirth());
                        return List.of(p); // Return a list with just this patient
                    }
                }
            }
        }
        
        System.out.println("No matching patients found");
        return List.of(); // Return empty list
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
        existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
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
