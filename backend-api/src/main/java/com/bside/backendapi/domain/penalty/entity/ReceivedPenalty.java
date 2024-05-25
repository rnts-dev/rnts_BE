package com.bside.backendapi.domain.penalty.entity;


import com.bside.backendapi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor() //access = AccessLevel.PROTECTED
@AllArgsConstructor() //access = AccessLevel.PROTECTED
public class ReceivedPenalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "received_penalty_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalTime resTime;

    public Object getContent() {
        return penalty.getContent();
    }

    public Object getPenaltyType() {
        return penalty.getPenaltyType();
    }

    public Object getFine() {
        return penalty.getFine();
    }
}
