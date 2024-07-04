package com.bside.backendapi.domain.checkin.dto.response;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckinGetReponse {

    private Long checkinId;

    public static CheckinGetReponse of(final AppointmentMember appointmentMember){
        return new CheckinGetReponse(
                appointmentMember.getId()
        );
    }
}
