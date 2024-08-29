package com.bside.backendapi.domain.member.error;

import com.bside.backendapi.global.error.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
