package com.bside.backendapi.domain.penalty.dto.request;

import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor()
public class TempPenaltyCreateRequest {

    @Valid
    @NotNull(message = "PenaltyType은 필수값입니다.")
    private PenaltyType penaltyType;

    private String content;


    public Penalty toEntity(){
        return Penalty.builder()
                .penaltyType(penaltyType)
                .build();
    }

    public static TempPenaltyCreateRequest of(PenaltyType penaltyType, String content) {
        return new TempPenaltyCreateRequest(penaltyType, content);
    }

}
