package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Allergy;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.AllergyRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AllergyService {
    @Autowired
    private AllergyRepo allergyRepository;

    @Autowired
    private PatientRepo patientRepository;

    public Page<Allergy> getAllergiesByPatient(Long patientId, Pageable pageable) {
        System.out.println("inside get allergies by patient service");
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        System.out.println("patient" + patient);
        return allergyRepository.findAllByPatientId(patientId, pageable);
    }

    public Allergy getAllergyById(Long allergyId) {
        return allergyRepository.findById(allergyId)
                .orElseThrow(() -> new RuntimeException("Allergy not found"));
    }

    public Allergy createAllergy(Long patientId, Allergy allergy) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        System.out.println("post allergy patient" + patient);
        allergy.setPatient(patient);
        return allergyRepository.save(allergy);
    }

    public Allergy updateAllergy(Long allergyId, Allergy updatedAllergy) {
        Allergy existingAllergy = getAllergyById(allergyId);
        existingAllergy.setAllergyName(updatedAllergy.getAllergyName());
        existingAllergy.setNotes(updatedAllergy.getNotes());
        return allergyRepository.save(existingAllergy);
    }

    public void deleteAllergy(Long allergyId) {
        Allergy existingAllergy = getAllergyById(allergyId);
        allergyRepository.delete(existingAllergy);
    }
}

