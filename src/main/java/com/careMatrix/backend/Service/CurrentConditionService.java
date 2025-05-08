package com.careMatrix.backend.Service;

import com.careMatrix.backend.Entity.CurrentCondition;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.CurrentConditionRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CurrentConditionService {

    @Autowired
    private CurrentConditionRepo conditionRepository;

    @Autowired
    private PatientRepo patientRepository;

    public Page<CurrentCondition> getConditionsByPatient(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found");
        }
        return conditionRepository.findAllByPatientId(patientId, pageable);
    }

    public CurrentCondition getConditionById(Long conditionId) {
        return conditionRepository.findById(conditionId)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
    }

    public CurrentCondition createCondition(Long patientId, CurrentCondition condition) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        condition.setPatient(patient);
        return conditionRepository.save(condition);
    }

    public CurrentCondition updateCondition(Long conditionId, CurrentCondition updatedCondition) {
        CurrentCondition existingCondition = getConditionById(conditionId);
        existingCondition.setConditionName(updatedCondition.getConditionName());
        existingCondition.setDiagnosedDate(updatedCondition.getDiagnosedDate());
        existingCondition.setStatus(updatedCondition.getStatus());
        return conditionRepository.save(existingCondition);
    }

    public void deleteCondition(Long conditionId) {
        CurrentCondition existingCondition = getConditionById(conditionId);
        conditionRepository.delete(existingCondition);
    }
}