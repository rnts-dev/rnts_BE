package com.bside.backendapi.domain.penalty.application;


import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.appointment.error.AppointmentNotFound;
import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenalty;
import com.bside.backendapi.domain.penalty.domain.persist.ReceivedPenaltyRepository;
import com.bside.backendapi.domain.penalty.dto.response.PenaltyGetResponse;
import com.bside.backendapi.domain.penalty.error.PenaltyNotFoundExepception;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReceivedPenaltyRepository receivedPenaltyRepository;

    //패널티 생성 서비스
    public Long create(final Penalty penalty, final Long memberId, final  Long appointmentId){

        //패널티 사용자 추가 후 저장
        Penalty savedPenalty = penaltyRepository.save(penalty.addPenaltyCreatorId(memberId));//set쓰지말고

        //해당 appointment에 penaltyid 추가
        Appointment updatedAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );
        log.info("ps appointmentId: {}", updatedAppointment.getId());

        updatedAppointment.addPenalty(savedPenalty.getId());
        appointmentRepository.save(updatedAppointment);
        log.info("ps pENALTYID: {}", appointmentRepository.findById(1L).get().getPenaltyId());

        return savedPenalty.getId();
    }


    //약속에서 패널티 조회
    public PenaltyGetResponse findByAppointment(final Long appointmentId){

        log.info("ps findByAppointment appointmentId: {}", appointmentId);
        Appointment findAppointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new AppointmentNotFound(ErrorCode.APPOINTMENT_NOT_FOUND)
        );
        log.info("ps findAppointment: {}", findAppointment.getId());

        //조회성공 여부
        Long penaltyId = findAppointment.getPenaltyId();
        if (penaltyId == null){
            return PenaltyGetResponse.empty();
        }
        log.info("ps penaltyId: {}", penaltyId);

        Penalty getPenalty = penaltyRepository.findById(penaltyId).orElseThrow(
                () -> new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND)
        );

        return PenaltyGetResponse.of(getPenalty);
    }


    //패널티 등록 서비스 (패널티 받는 사람 등록)
    public Long addReceiver(final Long penaltyId, final Long memberId){

        ReceivedPenalty receivedPenalty = ReceivedPenalty.builder()
                .penaltyId(penaltyId)
                .memberId(memberId)
                .build();

        receivedPenaltyRepository.save(receivedPenalty);

        return penaltyId;
    }



    //내가 생성한 패널티 조회
    public List<PenaltyGetResponse> MyCreatedPenalties(final Long memberId){

        List<Penalty> penalties = penaltyRepository.findByPenaltyCreatorId(memberId);

        if (penalties == null || penalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }
        List<PenaltyGetResponse> penaltyResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        return penaltyResponses;
    }


    //내가 받은 패널티 조회
    public List<PenaltyGetResponse> myPenalties(final Long memberId){

        //memberid로 receivedPenalties(penaltyid + memberid) 가져오기
        List<ReceivedPenalty> receivedPenalties = receivedPenaltyRepository.findByMemberId(memberId);
        if (receivedPenalties == null || receivedPenalties.isEmpty()){
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }

        //receivedPenalties에서 penaltyid 추출
        List<Long> penaltyIds = receivedPenalties.stream()
                .map(ReceivedPenalty::getPenaltyId)
                .collect(Collectors.toList());

        //추출한 penaltyid로 penalty정보 추출
        List<Penalty> penalties = penaltyRepository.findAllById(penaltyIds);
        if (penalties.isEmpty()) {
            throw new PenaltyNotFoundExepception(ErrorCode.PENALTY_NOT_FOUND);
        }

        //penaltyResponses에 담아서 리턴
        List<PenaltyGetResponse> penaltyResponses = penalties.stream()
                .map(PenaltyGetResponse::of)
                .collect(Collectors.toList());

        return penaltyResponses;
    }



}
