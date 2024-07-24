package com.bside.backendapi.domain.member.domain.persist;

import com.bside.backendapi.domain.member.domain.util.TestPasswordEncoder;
import com.bside.backendapi.domain.member.domain.vo.Name;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {

    @Test
    @DisplayName("회원 정보를 업데이트 한다.")
    void update() {
        // given
        Member updateMember = Member.builder()
                .password(Password.from("123456"))
                .nickname(Nickname.from("닉네임_변경"))
                .name(Name.from("이름_변경"))
                .build();

        PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();

        // when


        // then
    }

}