package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.Consultation;
import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.ConsultationRepo;
import com.careMatrix.backend.Repo.DoctorRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepo consultationRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    public List<Consultation> getAllConsultations() {
        return consultationRepo.findAll();
    }

    public Consultation getConsultationById(Long consultationId) {
        return consultationRepo.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found"));
    }

    public Consultation createConsultation(Long doctorId, Long patientId, Consultation consultation) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        return consultationRepo.save(consultation);
    }

    public Consultation updateConsultation(Long consultationId, Consultation updatedConsultation) {
        Consultation existing = getConsultationById(consultationId);
        existing.setDate(updatedConsultation.getDate());
        existing.setTime(updatedConsultation.getTime());
        existing.setReason(updatedConsultation.getReason());
        return consultationRepo.save(existing);
    }

    public void deleteConsultation(Long consultationId) {
        Consultation existing = getConsultationById(consultationId);
        consultationRepo.delete(existing);
    }
}
