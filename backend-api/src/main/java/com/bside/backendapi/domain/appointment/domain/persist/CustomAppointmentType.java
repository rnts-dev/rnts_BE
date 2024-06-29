package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAppointmentType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_type_id")
    private Long id;

    @ManyToOne @JsonProperty("member")
    @JoinColumn(name = "member_id")
    private Member member;

    @Length(min = 1, max = 6)
    @Column(name = "type_name")
    private String typeName;

    @Builder
    private CustomAppointmentType(Long id, Member member, String typeName) {
        this.id = id;
        this.member = member;
        this.typeName = typeName;
    }
}