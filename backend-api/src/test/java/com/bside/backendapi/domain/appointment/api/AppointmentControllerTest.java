package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.util.GivenAppointment;
import com.bside.backendapi.domain.member.domain.WithAuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class AppointmentControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean AppointmentService appointmentService;
    @Autowired ObjectMapper objectMapper;

//    @Test
//    @DisplayName("요청 받은 약속 정보를 저장한다.")
//    @WithAuthUser
//    void 약속_생성() throws Exception {
//        // given
//        AppointmentCreateRequest appointmentCreateRequest = AppointmentCreateRequest.of(GivenAppointment.toEntity());
//        String body = objectMapper.writeValueAsString(appointmentCreateRequest);
//
//        // when, then
//        when(appointmentService.create(any(), any())).thenReturn(1L);
//
//        mockMvc.perform(post("/api/v1/appointments")
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }
}
