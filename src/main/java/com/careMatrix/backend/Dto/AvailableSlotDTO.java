package com.careMatrix.backend.Dto;

import java.time.LocalTime;

public class AvailableSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    public AvailableSlotDTO(LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
} 