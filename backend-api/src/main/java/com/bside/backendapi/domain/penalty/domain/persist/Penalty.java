package com.bside.backendapi.domain.penalty.domain.persist;

import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "penalty_type", nullable = false)
    private PenaltyType penaltyType;

    private String content;

    private int fine;

    private Long penaltyCreatorId;



    @Builder
    public Penalty(Long id, PenaltyType penaltyType, String content, int fine,
                   Long penaltyCreatorId, List<Long> penaltyReceivedMemberId) {
        this.id = id;
        this.penaltyType = penaltyType;
        this.content = content;
        this.fine = fine;
        this.penaltyCreatorId = penaltyCreatorId;
//        this.penaltyReceivedMemberId = penaltyReceivedMemberId;
    }

    // 비즈니스 로직 추가
    public Penalty addPenaltyCreatorId(final Long penaltyCreatorId){
        this.penaltyCreatorId = penaltyCreatorId;
        return this;
    }

    //패널티 등록 로직
//    public void addReceivedMember(Long memberId) {
//        if (this.penaltyReceivedMemberId == null) {
//            this.penaltyReceivedMemberId = new ArrayList<>();
//        }
//        this.penaltyReceivedMemberId.add(memberId);
//    }

}
