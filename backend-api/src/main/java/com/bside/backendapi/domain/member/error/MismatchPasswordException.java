package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.BusinessException;
import com.bside.backendapi.global.error.exception.ErrorCode;

public class MismatchPasswordException extends BusinessException {

    public MismatchPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
