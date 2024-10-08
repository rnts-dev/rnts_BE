//package com.bside.backendapi.domain.penalty.application;
//
//import com.bside.backendapi.domain.appointment.application.AppointmentService;
//import com.bside.backendapi.domain.appointment.domain.Appointment;
//import com.bside.backendapi.domain.appointment.exception.AppointmentNotFoundException;
//import com.bside.backendapi.domain.appointment.repository.AppointmentRepository;
//import com.bside.backendapi.domain.appointment.util.GivenAppointment;
//import com.bside.backendapi.domain.member.application.MemberService;
//import com.bside.backendapi.domain.member.domain.Member;
//import com.bside.backendapi.domain.member.dto.SignUpResponse;
//import com.bside.backendapi.domain.member.exception.MemberNotFoundException;
//import com.bside.backendapi.domain.member.repository.MemberRepository;
//import com.bside.backendapi.domain.member.util.GivenMember;
//import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
//import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
//import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
//import com.bside.backendapi.domain.penalty.dto.request.TempPenaltyCreateRequest;
//import com.bside.backendapi.global.error.exception.ErrorCode;
//import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class PenaltyServiceTest {
//
//    @Autowired
//    PenaltyService penaltyService;
//
//    @Autowired
//    AppointmentService appointmentService;
//
//    @Autowired
//    PenaltyRepository penaltyRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    AppointmentRepository appointmentRepository;
//
//    @Autowired
//    MemberService memberService;
//
//    static Member testMember;
//    static Appointment testAppointment;
//
//    static Long penaltyId;
//    @BeforeEach
//    void init(){
//        SignUpResponse signUpResponse = memberService.signUp(GivenMember.toEntity());
//        testMember = memberRepository.findById(signUpResponse.getId()).orElseThrow(
//                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)
//        );
//
//        Long testAppointmentId = appointmentService.create(GivenAppointment.toEntity(),
//                (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        testAppointment = appointmentRepository.findById(testAppointmentId).orElseThrow(
//                () -> new AppointmentNotFoundException(ErrorCode.APPOINTMENT_NOT_FOUND)
//        );
//    }
//
//
////    @Test
////    @DisplayName("패널티가 없는 상태에서 조회")
////    void findByAppointmentWithoutPenalty() {
////        // Given
////        Long appointmentId = testAppointment.getId();
////
////        // When
////        PenaltyGetResponse penaltyGetResponse = penaltyService.findByAppointment(appointmentId);
////
////        // Then
////        assertThat(penaltyGetResponse).isNotNull();
////        assertThat(penaltyGetResponse.getPenaltyId()).isNull(); // 패널티 ID가 null인지 확인
////    }
//
//    @Test
//    @DisplayName("패널티 생성")
//    @Transactional(readOnly = true)
//    void createPenalty(){
//        // Given
//        TempPenaltyCreateRequest request = getTempPenaltyCreateRequest(testMember.getId());
//
//        // When
//        Long penaltyId = penaltyService.create(request.toEntity(), testMember.getId(), testAppointment.getId());
//
//        // Then
//        assertThat(penaltyId).isNotNull(); // 검증: 패널티 ID가 null이 아닌지 확인
//
//        // 패널티가 실제로 DB에 저장되었는지 확인
//        Optional<Penalty> savedPenalty = penaltyRepository.findById(penaltyId);
//        assertThat(savedPenalty).isPresent(); // 검증: 저장된 패널티가 존재하는지 확인
//        assertThat(savedPenalty.get().getPenaltyType()).isEqualTo(PenaltyType.FINE); // 검증: 패널티 타입 확인
//        assertThat(savedPenalty.get().getContent()).isEqualTo("테스트 페널티"); // 검증: 패널티 내용 확인
//        assertThat(savedPenalty.get().getPenaltyCreatorId()).isEqualTo(testMember.getId()); // 검증: 생성자 ID 확인
//    }
//
////    @Test티
////    @DisplayName("패널티 조회")
////    @Transactional(readOnly = true)
////    void findPenaltyByAppointment(){
////        //given
////        Long appointmentId = testAppointment.getId();
////
////        //when
////        PenaltyGetResponse penaltyGetResponse = penaltyService.findByAppointment(appointmentId);
////
////        //then
////        assertThat(penaltyGetResponse).isNotNull();
////
////        assertThat(penaltyGetResponse.getPenaltyId()).isNotNull(); // 패널티 ID가 null이 아닌지 확인
////        assertThat(penaltyGetResponse.getPenaltyType()).isEqualTo(PenaltyType.FINE); // 예상하는 패널티 타입과 일치하는지 확인
////        // 기타 필요한 검증을 추가로 수행할 수 있습니다.
////
////    }
//
//
//
//    private TempPenaltyCreateRequest getTempPenaltyCreateRequest(Long ceatorId){
//        return new TempPenaltyCreateRequest(
//                PenaltyType.FINE,
//                "테스트 페널티"
//        );
//    }
//
//
//
//}
