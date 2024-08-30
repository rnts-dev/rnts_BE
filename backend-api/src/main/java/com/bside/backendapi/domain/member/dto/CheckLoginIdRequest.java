package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.vo.LoginId;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckLoginIdRequest {

    @Valid
    private LoginId loginId;

}