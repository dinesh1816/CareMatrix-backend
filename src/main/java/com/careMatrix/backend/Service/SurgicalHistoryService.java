package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.SurgicalHistory;
import com.careMatrix.backend.Repo.PatientRepo;
import com.careMatrix.backend.Repo.SurgicalHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class SurgicalHistoryService {

    @Autowired
    private SurgicalHistoryRepo surgicalHistoryRepo;

    @Autowired
    private PatientRepo patientRepo;

    public Page<SurgicalHistory> getSurgeriesByPatient(Long patientId, Pageable pageable) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return surgicalHistoryRepo.findAllByPatientId(patientId, pageable);
    }

    public SurgicalHistory getSurgeryById(Long surgeryId) {
        return surgicalHistoryRepo.findById(surgeryId)
                .orElseThrow(() -> new RuntimeException("Surgical history record not found"));
    }

    public SurgicalHistory createSurgery(Long patientId, SurgicalHistory surgery) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        surgery.setPatient(patient);
        return surgicalHistoryRepo.save(surgery);
    }

    public SurgicalHistory updateSurgery(Long surgeryId, SurgicalHistory updatedSurgery) {
        SurgicalHistory existing = getSurgeryById(surgeryId);
        existing.setSurgeryName(updatedSurgery.getSurgeryName());
        existing.setSurgeryDate(updatedSurgery.getSurgeryDate());
        existing.setHospital(updatedSurgery.getHospital());
        return surgicalHistoryRepo.save(existing);
    }

    public void deleteSurgery(Long surgeryId) {
        SurgicalHistory existing = getSurgeryById(surgeryId);
        surgicalHistoryRepo.delete(existing);
    }
}
