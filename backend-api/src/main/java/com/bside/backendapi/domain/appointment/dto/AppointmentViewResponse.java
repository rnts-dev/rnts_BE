package com.bside.backendapi.domain.appointment.dto;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointment.domain.vo.Title;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentViewResponse {

    private Long id;
    private Title title;
    private Long creatorId;
    private AppointmentType appointmentType;
    private LocalDateTime appointmentTime;
    private Location location;

    public static AppointmentViewResponse of(final Appointment appointment) {
        return new AppointmentViewResponse(
                appointment.getId(),
                appointment.getTitle(),
                appointment.getCreatorId(),
                appointment.getAppointmentType(),
                appointment.getAppointmentTime(),
                appointment.getLocation());
    }
}