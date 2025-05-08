package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.Consultation;
import com.careMatrix.backend.Service.ConsultationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @GetMapping("/{consultationId}")
    public Consultation getConsultation(@PathVariable Long consultationId) {
        return consultationService.getConsultationById(consultationId);
    }

    /**
     * Create a consultation.
     * Query parameters: doctorId, patientId.
     */
    @PostMapping
    public Consultation createConsultation(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestBody Consultation consultation
    ) {
        return consultationService.createConsultation(doctorId, patientId, consultation);
    }

    @PutMapping("/{consultationId}")
    public Consultation updateConsultation(
            @PathVariable Long consultationId,
            @RequestBody Consultation updatedConsultation
    ) {
        return consultationService.updateConsultation(consultationId, updatedConsultation);
    }

    @DeleteMapping("/{consultationId}")
    public void deleteConsultation(@PathVariable Long consultationId) {
        consultationService.deleteConsultation(consultationId);
    }
}
