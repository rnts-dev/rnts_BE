package com.bside.backendapi.domain.checkin.error;

import com.bside.backendapi.global.error.exception.EntityNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class CheckinNotFoundException extends EntityNotFoundException {

    public CheckinNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
