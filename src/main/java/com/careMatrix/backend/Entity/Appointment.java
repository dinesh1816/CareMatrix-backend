package com.careMatrix.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;


    private String reason;


    private String type;   // "In-person" or "Telemedicine"

    // Field for telemedicine appointments
    private String meetingLink;

    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;

    public Appointment() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getReason() { return reason; }
    public String getType() { return type; }
    public String getMeetingLink() { return meetingLink; }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setReason(String reason) { this.reason = reason; }
    public void setType(String type) { this.type = type; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
