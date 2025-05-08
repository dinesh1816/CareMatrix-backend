package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.CurrentPrescription;
import com.careMatrix.backend.Service.CurrentPrescriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/prescriptions")
public class CurrentPrescriptionController {

    @Autowired
    private CurrentPrescriptionService prescriptionService;

    @GetMapping
    public Page<CurrentPrescription> getPrescriptions(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("prescribedDate").descending()
                .and(Sort.by("createdAt").descending())
        );
        return prescriptionService.getPrescriptionsByPatient(patientId, pageable);
    }

    @GetMapping("/{prescriptionId}")
    public CurrentPrescription getPrescription(@PathVariable Long prescriptionId) {
        return prescriptionService.getPrescriptionById(prescriptionId);
    }

    @PostMapping
    public CurrentPrescription createPrescription(@PathVariable Long patientId, @RequestBody CurrentPrescription prescription) {
        return prescriptionService.createPrescription(patientId, prescription);
    }

    @PutMapping("/{prescriptionId}")
    public CurrentPrescription updatePrescription(@PathVariable Long prescriptionId, @RequestBody CurrentPrescription updatedPrescription) {
        return prescriptionService.updatePrescription(prescriptionId, updatedPrescription);
    }

    @DeleteMapping("/{prescriptionId}")
    public void deletePrescription(@PathVariable Long prescriptionId) {
        prescriptionService.deletePrescription(prescriptionId);
    }
}
