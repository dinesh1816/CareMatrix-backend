package com.careMatrix.backend.Service;

import com.careMatrix.backend.Dto.AvailableSlotDTO;
import com.careMatrix.backend.Entity.Appointment;
import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Repo.AppointmentRepo;
import com.careMatrix.backend.Repo.DoctorRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    public Page<Appointment> getAllAppointments(Pageable pageable) {
        return appointmentRepo.findAll(pageable);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Appointment createAppointment(Long doctorId, Long patientId, Appointment appointment) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        // Check if the slot is already taken
        Optional<Appointment> existingSlot = appointmentRepo.findByDoctorAndDateAndTime(
                doctor, appointment.getDate(), appointment.getTime());
        if(existingSlot.isPresent()){
            throw new RuntimeException("Slot unavailable");
        }

        // If Telemedicine, generate a meeting link
        if ("Telemedicine".equalsIgnoreCase(appointment.getType())) {
            appointment.setMeetingLink(generateMeetingLink());
        }
        return appointmentRepo.save(appointment);
    }

    public Appointment updateAppointment(Long appointmentId, Appointment updatedAppointment) {
        Appointment existing = getAppointmentById(appointmentId);
        existing.setDate(updatedAppointment.getDate());
        existing.setTime(updatedAppointment.getTime());
        existing.setReason(updatedAppointment.getReason());
        existing.setType(updatedAppointment.getType());
        // Optionally, update meeting link if the type is telemedicine and no link exists
        if ("Telemedicine".equalsIgnoreCase(updatedAppointment.getType()) &&
                (existing.getMeetingLink() == null || existing.getMeetingLink().isEmpty())) {
            existing.setMeetingLink(generateMeetingLink());
        }
        return appointmentRepo.save(existing);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment existing = getAppointmentById(appointmentId);
        appointmentRepo.delete(existing);
    }

    // Helper method to generate a dummy meeting link
    private String generateMeetingLink() {
        String uniqueCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
        return "https://meet.google.com/" + uniqueCode;
    }


    //to get appointments with patients
    public Map<String, List<Appointment>> getLatestAndUpcomingAppointments(Long patientId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> latest2 = appointmentRepo
                .findTop2ByPatientId_IdAndDateLessThanEqualAndTimeLessThanOrderByDateDescTimeDesc(patientId, today, now);

        List<Appointment> upcoming2 = appointmentRepo
                .findTop2ByPatientId_IdAndDateGreaterThanEqualAndTimeGreaterThanOrderByDateAscTimeAsc(patientId, today, now);

        Map<String, List<Appointment>> result = new HashMap<>();
        result.put("latest", latest2);
        result.put("upcoming", upcoming2);

        return result;
    }

    //to get appointments doctor
    public Map<String, List<Appointment>> getLatestAndUpcomingAppointmentsForDoctor(Long doctorId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> latest2 = appointmentRepo
                .findTop2ByDoctor_IdAndDateLessThanEqualAndTimeLessThanOrderByDateDescTimeDesc(doctorId, today, now);

        List<Appointment> upcoming2 = appointmentRepo
                .findTop2ByDoctor_IdAndDateGreaterThanEqualAndTimeGreaterThanOrderByDateAscTimeAsc(doctorId, today, now);

        Map<String, List<Appointment>> result = new HashMap<>();
        result.put("latest", latest2);
        result.put("upcoming", upcoming2);

        return result;
    }


    public Page<Appointment> getAppointmentsByPatientId(Long patientId, Pageable pageable) {
        return appointmentRepo.findByPatient_Id(patientId, pageable);
    }

    public Page<Appointment> getAppointmentsByDoctorId(Long doctorId, Pageable pageable) {
        return appointmentRepo.findByDoctor_Id(doctorId, pageable);
    }

    public List<AvailableSlotDTO> getAvailableSlots(Long doctorId, Long patientId, LocalDate date) {
        // Get all appointments for the doctor on the given date
        List<Appointment> existingAppointments = appointmentRepo.findByDoctor_IdAndDate(doctorId, date);
        
        // Define working hours (9 AM to 5 PM)
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        // Define appointment duration (30 minutes)
        int appointmentDurationMinutes = 30;
        
        // Get booked time slots
        Set<LocalTime> bookedSlots = existingAppointments.stream()
                .map(Appointment::getTime)
                .collect(Collectors.toSet());
        
        List<AvailableSlotDTO> availableSlots = new ArrayList<>();
        LocalTime currentSlot = startTime;
        
        // Generate slots for the entire day
        while (currentSlot.plusMinutes(appointmentDurationMinutes).isBefore(endTime) || 
               currentSlot.plusMinutes(appointmentDurationMinutes).equals(endTime)) {
            LocalTime slotEnd = currentSlot.plusMinutes(appointmentDurationMinutes);
            boolean isAvailable = !bookedSlots.contains(currentSlot);
            
            availableSlots.add(new AvailableSlotDTO(currentSlot, slotEnd, isAvailable));
            currentSlot = slotEnd;
        }
        
        return availableSlots;
    }

}
