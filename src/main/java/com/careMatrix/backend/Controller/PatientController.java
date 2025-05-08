package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/search")
    public List<Patient> findByNameAndAge(@RequestParam String name, @RequestParam int age){
        return patientService.getByNameAndAge(name, age);
    }


    @GetMapping("/get/{patientId}")
    public Patient getPatient(@PathVariable Long patientId) {
        return patientService.getPatientById(patientId);
    }

    @PostMapping("/save")
    public Patient createPatient( @RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{patientId}")
    public Patient updatePatient(@PathVariable Long patientId, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(patientId, updatedPatient);
    }

    @DeleteMapping("/{patientId}")
    public void deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
    }
}
