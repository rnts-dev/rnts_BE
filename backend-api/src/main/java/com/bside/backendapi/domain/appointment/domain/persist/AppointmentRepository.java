package com.bside.backendapi.domain.appointment.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByCreatorId(Long memberId);

    Optional<Appointment> findByCustomAppointmentTypeId(Long customAppointmentTypeId);
}
