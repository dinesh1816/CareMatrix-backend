package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.InsuranceInformation;
import com.careMatrix.backend.Service.InsuranceInformationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients/{patientId}/insurance")
public class InsuranceInformationController {

    @Autowired
    private InsuranceInformationService insuranceService;

    @GetMapping
    public Page<InsuranceInformation> getInsuranceInfo(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("expiryDate").descending()
                .and(Sort.by("createdAt").descending())
        );
        return insuranceService.getInsuranceByPatient(patientId, pageable);
    }

    @GetMapping("/{insuranceId}")
    public InsuranceInformation getInsuranceById(@PathVariable Long insuranceId) {
        return insuranceService.getInsuranceById(insuranceId);
    }

    @PostMapping
    public InsuranceInformation createInsurance(@PathVariable Long patientId,
                                                @RequestBody InsuranceInformation insurance) {
        return insuranceService.createInsurance(patientId, insurance);
    }

    @PutMapping("/{insuranceId}")
    public InsuranceInformation updateInsurance(@PathVariable Long insuranceId,
                                                @RequestBody InsuranceInformation updatedInsurance) {
        return insuranceService.updateInsurance(insuranceId, updatedInsurance);
    }

    @DeleteMapping("/{insuranceId}")
    public void deleteInsurance(@PathVariable Long insuranceId) {
        insuranceService.deleteInsurance(insuranceId);
    }
}
