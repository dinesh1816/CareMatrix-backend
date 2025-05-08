package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.InsuranceInformation;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.InsuranceInformationRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InsuranceInformationService {

    @Autowired
    private InsuranceInformationRepo insuranceRepo;

    @Autowired
    private PatientRepo patientRepo;

    public Page<InsuranceInformation> getInsuranceByPatient(Long patientId, Pageable pageable) {
        return insuranceRepo.findByPatient_Id(patientId, pageable);
    }

    public InsuranceInformation getInsuranceById(Long insuranceId) {
        return insuranceRepo.findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Insurance record not found"));
    }

    public InsuranceInformation createInsurance(Long patientId, InsuranceInformation insurance) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        insurance.setPatient(patient);
        return insuranceRepo.save(insurance);
    }

    public InsuranceInformation updateInsurance(Long insuranceId, InsuranceInformation updatedInsurance) {
        InsuranceInformation existing = getInsuranceById(insuranceId);
        existing.setPolicyNumber(updatedInsurance.getPolicyNumber());
        existing.setExpiryDate(updatedInsurance.getExpiryDate());
        existing.setCoverage(updatedInsurance.getCoverage());
        return insuranceRepo.save(existing);
    }

    public void deleteInsurance(Long insuranceId) {
        InsuranceInformation existing = getInsuranceById(insuranceId);
        insuranceRepo.delete(existing);
    }
}
