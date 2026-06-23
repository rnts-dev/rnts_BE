package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    @Valid @NotNull(message = "필수 입력값입니다. - loginId")
    private LoginId loginId;

    @Valid @NotNull(message = "필수 입력값입니다. - password")
    private Password password;

    @Valid @NotNull(message = "필수 입력값입니다. - mail")
    private Mail mail;

    @Valid @NotNull(message = "필수 입력값입니다. - nickname")
    private Nickname nickname;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .mail(mail)
                .nickname(nickname)
                .role(RoleType.USER)
                .profileImage("https://kr.object.ncloudstorage.com/rnts/profile/1.jpg")
                .build();
    }
}
