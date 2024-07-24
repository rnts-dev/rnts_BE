package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.dto.AppointmentCreateRequest;
import com.bside.backendapi.domain.appointment.util.GivenAppointment;
import com.bside.backendapi.domain.member.domain.WithAuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // MOCK 환경에서 테스트
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean AppointmentService appointmentService;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("요청 받은 약속 정보를 저장한다.")
    @WithAuthUser
    void 약속_생성() throws Exception {
        // given
        AppointmentCreateRequest appointmentCreateRequest = AppointmentCreateRequest.of(GivenAppointment.toEntity());
        String body = objectMapper.writeValueAsString(appointmentCreateRequest);

        // when, then
        when(appointmentService.create(any(), any())).thenReturn(1L);

        mockMvc.perform(post("/api/v1/appointments")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("application/create",
                        requestFields(
                                fieldWithPath("title").description("약속 제목"),
                                fieldWithPath("appointmentType").description("약속 유형"),
                                fieldWithPath("customAppointmentType").description("사용자 정의 약속 유형"),
                                fieldWithPath("appointmentTime").description("약속 시간"),
                                fieldWithPath("location.place").description("약속 장소의 이름"),
                                fieldWithPath("location.latitude").description("약속 장소의 위도"),
                                fieldWithPath("location.longitude").description("약속 장소의 경도")
                                ), responseFields(
                                fieldWithPath("appointmentId").description("약속 식별자")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("동일한 날짜에 약속 생성 시 동시성 이슈 테스트")
    @WithAuthUser
    void 날짜_중복_생성_동시성_테스트() throws Exception {
        // given
        AppointmentCreateRequest appointmentCreateRequest = AppointmentCreateRequest.of(GivenAppointment.toEntity());
        String body = objectMapper.writeValueAsString(appointmentCreateRequest);

        when(appointmentService.create(any(), any())).thenAnswer(invocation -> {
            Appointment appointment = invocation.getArgument(0);
            Long memberId = invocation.getArgument(1);

            Thread.sleep(100);
            return 1L;
        });

        Runnable task = () -> {
            try {
                mockMvc.perform(post("/api/v1/appointments")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        };

        // when
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // then
        verify(appointmentService, times(2)).create(any(), any());
    }

}
