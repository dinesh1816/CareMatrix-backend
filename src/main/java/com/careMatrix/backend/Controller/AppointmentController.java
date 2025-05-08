package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Dto.AvailableSlotDTO;
import com.careMatrix.backend.Entity.Appointment;
import com.careMatrix.backend.Service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public Page<Appointment> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("date").ascending()
                .and(Sort.by("time").ascending())
                .and(Sort.by("createdAt").ascending())
        );
        return appointmentService.getAllAppointments(pageable);
    }

    @GetMapping("/{appointmentId}")
    public Appointment getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    /**
     * Create an appointment.
     * Query parameters: doctorId, patientId.
     * In case of Telemedicine, the meeting link is automatically generated.
     */
    @PostMapping("/create")
    public Appointment createAppointment(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestBody Appointment appointment
    ) {
        return appointmentService.createAppointment(doctorId, patientId, appointment);
    }

    @PutMapping("/{appointmentId}")
    public Appointment updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody Appointment updatedAppointment
    ) {
        return appointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }

    //to get latest and upcoming 2 patient
    @GetMapping("/patient/{patientId}/latest-upcoming")
    public Map<String, List<Appointment>> getLatestAndUpcomingAppointments(@PathVariable Long patientId) {
        return appointmentService.getLatestAndUpcomingAppointments(patientId);
    }

    //to get latest and upcoming 2 doctor
    @GetMapping("/doctor/{doctorId}/latest-upcoming")
    public Map<String, List<Appointment>> getLatestAndUpcomingAppointmentsForDoctor(@PathVariable Long doctorId) {
        return appointmentService.getLatestAndUpcomingAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}/paginated")
    public Page<Appointment> getAppointmentsByPatientPaginated(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("date").ascending()
                .and(Sort.by("time").ascending())
                .and(Sort.by("createdAt").ascending())
        );
        return appointmentService.getAppointmentsByPatientId(patientId, pageable);
    }

    @GetMapping("/doctor/{doctorId}/paginated")
    public Page<Appointment> getAppointmentsByDoctorPaginated(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by("date").ascending()
                .and(Sort.by("time").ascending())
                .and(Sort.by("createdAt").ascending())
        );
        return appointmentService.getAppointmentsByDoctorId(doctorId, pageable);
    }

    @GetMapping("/doctor/{doctorId}/patient/{patientId}/available-slots")
    public List<AvailableSlotDTO> getAvailableSlots(
            @PathVariable Long doctorId,
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return appointmentService.getAvailableSlots(doctorId, patientId, date);
    }

    @PostMapping("/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long appointmentId,
            @RequestParam Long doctorId
    ) {
        appointmentService.cancelAppointment(appointmentId, doctorId);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }
}
