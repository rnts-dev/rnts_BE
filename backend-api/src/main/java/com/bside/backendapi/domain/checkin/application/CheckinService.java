package com.bside.backendapi.domain.checkin.application;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.checkin.dto.response.CheckinGetReponse;
import com.bside.backendapi.domain.checkin.dto.response.CheckinResponse;
import com.bside.backendapi.domain.checkin.error.CheckinNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckinService {

    private final AppointmentMemberRepository appointmentMemberRepository;
    private final AppointmentService appointmentService;

    public LocalDateTime getCurrentTimeInKorea() {
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowInKorea.toLocalDateTime();
    }

    public CheckinGetReponse findByAppointmentId(final Long appointmentId, final Long memberId){

        Optional<AppointmentMember> appointmentMemberOptional = appointmentMemberRepository.findByAppointmentIdAndMemberId(appointmentId, memberId);

        if (appointmentMemberOptional.isPresent()) {
            AppointmentMember appointmentMember = appointmentMemberOptional.get();
            return CheckinGetReponse.of(appointmentMember);
        }
        else{
            throw new CheckinNotFoundException(ErrorCode.CHECKIN_NOT_FOUND);
        }

    }

    public CheckinResponse checkin(final Long chekcinId){

        AppointmentMember appointmentMember = appointmentMemberRepository.findById(chekcinId).orElseThrow(
                () -> new CheckinNotFoundException(ErrorCode.CHECKIN_NOT_FOUND)
        );
        Appointment appointment = appointmentMember.getAppointment();

        //1등여부
        boolean checkFirst = appointment.isFirst();
        if(!checkFirst){
            appointment.setIsFirst();
        }

        //지각여부
        LocalDateTime aptime = appointment.getAppointmentTime();
        LocalDateTime nowTime = getCurrentTimeInKorea();
        boolean isLate = nowTime.isAfter(aptime);

        //남은시간
        Long restime = ChronoUnit.MINUTES.between(aptime, nowTime);

        return CheckinResponse.of(appointment.getId(),isLate,checkFirst, restime);
    }


}
