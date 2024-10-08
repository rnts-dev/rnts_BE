package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.AppointmentMember;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.exception.AppointmentErrorCode;
import com.bside.backendapi.domain.appointment.exception.AppointmentException;
import com.bside.backendapi.domain.appointment.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentViewService {

    private final AppointmentMemberRepository appointmentMemberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public List<AppointmentResponse> getAllAppointments(final CustomOAuth2User principal) {
        return getAppointmentsByFilter(principal, appointment -> true);
    }

    public List<AppointmentResponse> getPastAppointments(final CustomOAuth2User principal) {
        return getAppointmentsByFilter(principal,
                appointment -> appointment.getAppointmentTime().isBefore(LocalDateTime.now()));
    }

    public List<AppointmentResponse> getUpcomingAppointments(final CustomOAuth2User principal) {
        return getAppointmentsByFilter(principal,
                appointment -> appointment.getAppointmentTime().isAfter(LocalDateTime.now()));
    }

    public List<AppointmentResponse> getAppointmentsByFilter(final CustomOAuth2User principal,
                                                             Predicate<Appointment> filter) {
        return appointmentMemberRepository.findAllWithAppointmentsByMemberId(principal.getMember().getId())
                .stream()
                .map(AppointmentMember::getAppointment)
                .filter(filter)
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    private CustomAppointmentType getCustomAppointmentType(final Long customAppointmentId) {
        return customAppointmentTypeRepository.findById(customAppointmentId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.CUSTOM_TYPE_NOT_FOUND));
    }

    private AppointmentResponse mapToAppointmentResponse(final Appointment appointment) {
        if (appointment.getCustomAppointmentTypeId() != 0L)
            return AppointmentResponse.of(appointment, getCustomAppointmentType(appointment.getCustomAppointmentTypeId()));
        else{
            return AppointmentResponse.of(appointment);
        }
    }
}
