package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.SurgicalHistory;
import com.careMatrix.backend.Service.SurgicalHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/surgical-history")
public class SurgicalHistoryController {

    @Autowired
    private SurgicalHistoryService surgicalHistoryService;

    @GetMapping
    public Page<SurgicalHistory> getSurgeries(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("surgeryDate").descending()
                .and(Sort.by("createdAt").descending())
        );
        return surgicalHistoryService.getSurgeriesByPatient(patientId, pageable);
    }

    @GetMapping("/{surgeryId}")
    public SurgicalHistory getSurgery(@PathVariable Long surgeryId) {
        return surgicalHistoryService.getSurgeryById(surgeryId);
    }

    @PostMapping
    public SurgicalHistory createSurgery(@PathVariable Long patientId,
                                         @RequestBody SurgicalHistory surgery) {
        return surgicalHistoryService.createSurgery(patientId, surgery);
    }

    @PutMapping("/{surgeryId}")
    public SurgicalHistory updateSurgery(@PathVariable Long surgeryId,
                                         @RequestBody SurgicalHistory updatedSurgery) {
        return surgicalHistoryService.updateSurgery(surgeryId, updatedSurgery);
    }

    @DeleteMapping("/{surgeryId}")
    public void deleteSurgery(@PathVariable Long surgeryId) {
        surgicalHistoryService.deleteSurgery(surgeryId);
    }
}
