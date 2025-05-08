package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.CurrentCondition;
import com.careMatrix.backend.Service.CurrentConditionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/conditions")
public class CurrentConditionController {

    @Autowired
    private CurrentConditionService conditionService;

    @GetMapping
    public Page<CurrentCondition> getConditions(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("diagnosedDate").descending()
                .and(Sort.by("createdAt").descending())
        );
        return conditionService.getConditionsByPatient(patientId, pageable);
    }

    @GetMapping("/{conditionId}")
    public CurrentCondition getCondition(@PathVariable Long conditionId) {
        return conditionService.getConditionById(conditionId);
    }

    @PostMapping
    public CurrentCondition createCondition(@PathVariable Long patientId,@RequestBody CurrentCondition condition) {
        return conditionService.createCondition(patientId, condition);
    }

    @PutMapping("/{conditionId}")
    public CurrentCondition updateCondition(@PathVariable Long conditionId,@RequestBody CurrentCondition updatedCondition) {
        return conditionService.updateCondition(conditionId, updatedCondition);
    }

    @DeleteMapping("/{conditionId}")
    public void deleteCondition(@PathVariable Long conditionId) {
        conditionService.deleteCondition(conditionId);
    }
}
