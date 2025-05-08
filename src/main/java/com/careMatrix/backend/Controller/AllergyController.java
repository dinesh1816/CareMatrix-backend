package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.Allergy;
import com.careMatrix.backend.Service.AllergyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/allergies")
//@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class AllergyController {

    @Autowired
    private AllergyService allergyService;

    @GetMapping
    public Page<Allergy> getAllergies(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return allergyService.getAllergiesByPatient(patientId, pageable);
    }

    @GetMapping("/{allergyId}")
    public Allergy getAllergy(@PathVariable Long allergyId) {
        return allergyService.getAllergyById(allergyId);
    }

    @PostMapping
    public Allergy createAllergy(@PathVariable Long patientId,@RequestBody Allergy allergy) {
        System.out.println("inside post allergy" + allergy);
        return allergyService.createAllergy(patientId, allergy);
    }

    @PutMapping("/{allergyId}")
    public Allergy updateAllergy(@PathVariable Long allergyId,@RequestBody Allergy updatedAllergy) {
        return allergyService.updateAllergy(allergyId, updatedAllergy);
    }

    @DeleteMapping("/{allergyId}")
    public void deleteAllergy(@PathVariable Long allergyId) {
        allergyService.deleteAllergy(allergyId);
    }
}
