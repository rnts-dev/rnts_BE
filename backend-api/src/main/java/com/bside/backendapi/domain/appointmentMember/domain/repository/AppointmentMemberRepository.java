package com.bside.backendapi.domain.appointmentMember.domain.repository;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentMemberRepository extends JpaRepository<AppointmentMember, Long> {

    List<AppointmentMember> findAllByMemberId(Long memberId);

    List<AppointmentMember> deleteByAppointmentIdAndMemberId(Long appointmentId, Long memberId);

    Optional<AppointmentMember> findByAppointmentIdAndMemberId(Long appointmentId, Long memberId);

}
