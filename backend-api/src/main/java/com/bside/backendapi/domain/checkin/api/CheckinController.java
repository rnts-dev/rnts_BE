package com.bside.backendapi.domain.checkin.api;


import com.bside.backendapi.domain.checkin.application.CheckinService;
import com.bside.backendapi.domain.checkin.dto.response.CheckinGetReponse;
import com.bside.backendapi.domain.checkin.dto.response.CheckinResponse;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Appointment", description = "Appointment API Document")
public class CheckinController {

    private final CheckinService checkinService;

    //체크인 id 조회
    @GetMapping("/checkin/{appointmentId}")
    public ResponseEntity<CheckinGetReponse> findCheckinIdByAppointmentId(@PathVariable Long appointmentId){
        return ResponseEntity.ok().body(checkinService.findByAppointmentId(appointmentId, this.getPrincipal().getId()));
    }


    //체크인
    @PostMapping("/checkin/{checkinId}")
    public ResponseEntity<CheckinResponse> checkin(@PathVariable Long checkinId){
        return ResponseEntity.ok().body(checkinService.checkin(checkinId));
    }


    private CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
