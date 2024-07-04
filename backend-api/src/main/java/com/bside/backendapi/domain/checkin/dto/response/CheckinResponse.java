package com.bside.backendapi.domain.checkin.dto.response;

public class CheckinResponse {

    private Long appointmentId;

    private boolean isLate;

    private boolean isFirst;

    private Long resTime;

    public static CheckinResponse of(Long appointmentId, boolean isLate, boolean isFirst, Long resTime) {
        CheckinResponse response = new CheckinResponse();
        response.appointmentId = appointmentId;
        response.isLate = isLate;
        response.isFirst = isFirst;
        response.resTime = resTime;
        return response;
    }
}
