package com.bside.backendapi.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CheckinResponse {

    private boolean isLate;
    private boolean isFirst;
    private Long resTime;
//    private CheckInType checkInType;
//    private ArriveType arriveType;

}
