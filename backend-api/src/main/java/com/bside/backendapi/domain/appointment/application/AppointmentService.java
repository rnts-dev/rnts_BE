package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.AppointmentMember;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.dto.AppointmentDetailsResponse;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.exception.AppointmentErrorCode;
import com.bside.backendapi.domain.appointment.exception.AppointmentException;
import com.bside.backendapi.domain.appointment.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
import com.bside.backendapi.domain.appointment.repository.CustomAppointmentTypeRepository;
import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.exception.MemberErrorCode;
import com.bside.backendapi.domain.member.exception.MemberException;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final MemberRepository memberRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMemberRepository appointmentMemberRepository;
    private final CustomAppointmentTypeRepository customAppointmentTypeRepository;

    public AppointmentResponse create(final Appointment appointment, final CustomOAuth2User principal) {
        Appointment newAppointment = appointment.create(principal.getId(), appointment);
        appointmentRepository.save(newAppointment);

        saveAppointmentMember(newAppointment, principal);

        // 사용자 정의 약속 유형의 ID가 있는 경우 해당 유형 이름과 함께 약속 반환
        if (appointment.getCustomAppointmentTypeId() != 0L) {
            CustomAppointmentType customAppointmentType = customAppointmentTypeRepository.findById(appointment.getCustomAppointmentTypeId())
                    .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.CUSTOM_TYPE_NOT_FOUND));
            return AppointmentResponse.of(newAppointment, customAppointmentType);
        }
        else
            return AppointmentResponse.of(newAppointment);
    }

    public AppointmentDetailsResponse getAppointmentDetails(final Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);

        // 초대한 사람 이름 찾기
        Member creator = memberRepository.findById(appointment.getCreatorId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return AppointmentDetailsResponse.of(creator.getNickname(), appointment);
    }

    public void delete(final Long appointmentId, final CustomOAuth2User principal) {
        appointmentMemberRepository.deleteByAppointmentIdAndMemberId(appointmentId, getMember(principal).getId());

        // 멤버가 약속에서 빠질 때 해당 약속 id가 포함된 중간테이블의 레코드가 없을 경우 해당 약속의 멤버가 없음 -> 약속 삭제
        if (appointmentMemberRepository.findAllByAppointmentId(appointmentId).isEmpty())
            appointmentRepository.deleteById(appointmentId);
    }

    public void acceptInvite(final Long appointmentId, final CustomOAuth2User principal) {
        saveAppointmentMember(getAppointment(appointmentId), principal);
    }

    public void udpate(final Appointment updateAppointment, final Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);
        appointment.update(updateAppointment);
    }

    private void saveAppointmentMember(final Appointment appointment, final CustomOAuth2User principal) {
        Member member = getMember(principal);

        AppointmentMember newAppointmentMember = AppointmentMember.builder()
                .appointment(appointment).member(member).build();

        appointmentMemberRepository.save(newAppointmentMember);
    }

    private Appointment getAppointment(final Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException(AppointmentErrorCode.APPOINTMENT_NOT_FOUND));
    }

    private Member getMember(final CustomOAuth2User principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

}