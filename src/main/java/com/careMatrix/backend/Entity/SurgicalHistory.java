package com.careMatrix.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class SurgicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surgeryName;
    private LocalDate surgeryDate;
    private String hospital;

    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Constructors
    public SurgicalHistory() {
        this.createdAt = LocalDateTime.now();
    }

    public SurgicalHistory(String surgeryName, LocalDate surgeryDate, String hospital, Patient patient) {
        this.surgeryName = surgeryName;
        this.surgeryDate = surgeryDate;
        this.hospital = hospital;
        this.patient = patient;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public String getSurgeryName() {
        return surgeryName;
    }
    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
    }
    public LocalDate getSurgeryDate() {
        return surgeryDate;
    }
    public void setSurgeryDate(LocalDate surgeryDate) {
        this.surgeryDate = surgeryDate;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
