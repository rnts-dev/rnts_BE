package com.bside.backendapi.domain.penalty.repository;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    Penalty findPenaltyByUserId(Long userId);

    Penalty findPenaltyByUserIdAndAppointmentId(Long userId, Long appointmentId);

}
