package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.domain.member.vo.Password;
import com.bside.backendapi.domain.member.vo.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private LoginId loginId;
    private Password password;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .role(RoleType.USER)
                .build();
    }
}