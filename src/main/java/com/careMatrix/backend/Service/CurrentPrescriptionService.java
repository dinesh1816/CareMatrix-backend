package com.careMatrix.backend.Service;


import com.careMatrix.backend.Entity.CurrentPrescription;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.CurrentPrescriptionRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CurrentPrescriptionService {

    @Autowired
    private CurrentPrescriptionRepo prescriptionRepository;

    @Autowired
    private PatientRepo patientRepository;

    public Page<CurrentPrescription> getPrescriptionsByPatient(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found");
        }
        return prescriptionRepository.findAllByPatientId(patientId, pageable);
    }

    public CurrentPrescription getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    public CurrentPrescription createPrescription(Long patientId, CurrentPrescription prescription) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        prescription.setPatient(patient);
        return prescriptionRepository.save(prescription);
    }

    public CurrentPrescription updatePrescription(Long prescriptionId, CurrentPrescription updatedPrescription) {
        CurrentPrescription existingPrescription = getPrescriptionById(prescriptionId);
        existingPrescription.setMedicationName(updatedPrescription.getMedicationName());
        existingPrescription.setDosage(updatedPrescription.getDosage());
        existingPrescription.setFrequency(updatedPrescription.getFrequency());
        existingPrescription.setPrescribedDate(updatedPrescription.getPrescribedDate());
        existingPrescription.setEndDate(updatedPrescription.getEndDate());
        return prescriptionRepository.save(existingPrescription);
    }

    public void deletePrescription(Long prescriptionId) {
        CurrentPrescription existingPrescription = getPrescriptionById(prescriptionId);
        prescriptionRepository.delete(existingPrescription);
    }
}
