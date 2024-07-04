package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.util.GivenAppointment;
import com.bside.backendapi.domain.member.application.MemberService;
import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.dto.MemberResponse;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.domain.member.util.GivenMember;
import com.bside.backendapi.global.error.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AppointmentServiceTest {

    @Autowired AppointmentService appointmentService;
    @Autowired AppointmentRepository appointmentRepository;
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    static Member member;
    static Long appointmentId;

    @BeforeEach
    void init() {
        MemberResponse joinResponse = memberService.join(GivenMember.toEntity());

        member = memberRepository.findById(joinResponse.getMemberId()).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND)
        );

        appointmentId = appointmentService.create(GivenAppointment.toEntityWithId(), null, member.getId());
    }

}
