package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Service.DoctorService;
import com.careMatrix.backend.Service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
//@CrossOrigin(origins = {"http://127.0.0.1:3000","http://127.0.0.1:8080"},allowCredentials="true")
@CrossOrigin(origins = "http://localhost:3000/doctor-dashboard")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // ----- Doctor Endpoints -----
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{doctorId}")
    public Doctor getDoctor(@PathVariable Long doctorId) {
        return doctorService.getDoctorById(doctorId);
    }

    @PostMapping("/save")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    @PutMapping("/{doctorId}")
    public Doctor updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor updatedDoctor) {
        return doctorService.updateDoctor(doctorId, updatedDoctor);
    }

    @DeleteMapping("/{doctorId}")
    public void deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
    }

    // ----- Patient Access for Doctor -----
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{patientId}")
    public Patient getPatientById(@PathVariable Long patientId) {
        return patientService.getPatientById(patientId);
    }

    // Search patients by name (partial, case-insensitive)
    @GetMapping("/patients/search")
    public List<Patient> searchPatientsByName(@RequestParam String name) {
        return patientService.searchByName(name);
    }

    // Search patients by name and date of birth
    @GetMapping("/patients/search/by-name-dob")
    public ResponseEntity<?> searchPatientsByNameAndDob(
            @RequestParam String name,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth
    ) {
        List<Patient> patients = patientService.getByNameAndDateOfBirth(name, dateOfBirth);
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patients.get(0));
    }
}
