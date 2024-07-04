package com.bside.backendapi.domain.appointment.domain.persist;

import com.bside.backendapi.domain.appointment.domain.vo.AppointmentType;
import com.bside.backendapi.domain.appointment.domain.vo.Location;
import com.bside.backendapi.domain.appointment.domain.vo.Title;
import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Title title;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "appointment_type")
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "custom_type_id")
    private CustomAppointmentType customAppointmentType;

    @Column(name = "appointment_time", nullable = false)
    private LocalDateTime appointmentTime;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "is_first")
    private boolean isFirst = false;



    @Column(name = "penalty_id")
    private Long penaltyId;

    //    @OneToMany(mappedBy = "appointment")
//    private List<AppointmentMember> members;

    @Builder
    private Appointment(Long id, Title title, Long creatorId, AppointmentType appointmentType, CustomAppointmentType customAppointmentType, LocalDateTime appointmentTime,
                        Location location, boolean isDeleted, boolean isFirst) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.appointmentType = appointmentType;
        this.customAppointmentType = customAppointmentType;
        this.appointmentTime = appointmentTime;
        this.location = location;
        this.isDeleted = false;
        this.isFirst = false;
    }

    // 비즈니스 로직 추가
    public Appointment create(final Long creatorId, final AppointmentType appointmentType, final CustomAppointmentType customAppointmentType) {
        this.creatorId = creatorId;
        this.appointmentType = appointmentType;
        this.customAppointmentType = customAppointmentType;
        return this;
    }

    //패널티 추가
    public void addPenalty(final Long penaltyId) {
        this.penaltyId = penaltyId;
    }

    public void update(final Appointment updateAppointment) {
        this.title = updateAppointment.title;
        this.appointmentType = updateAppointment.appointmentType;
        this.appointmentTime = updateAppointment.appointmentTime;
        this.location = updateAppointment.location;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public boolean isFirst() {
        return isFirst;
    }
    public void setIsFirst(){
        this.isFirst = true;
    }
}



