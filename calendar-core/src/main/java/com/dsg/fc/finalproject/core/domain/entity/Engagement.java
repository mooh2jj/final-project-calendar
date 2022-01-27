package com.dsg.fc.finalproject.core.domain.entity;

import com.dsg.fc.finalproject.core.domain.Event;
import com.dsg.fc.finalproject.core.domain.RequestStatus;
import com.dsg.fc.finalproject.core.util.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "engagments")
@Entity
public class Engagement extends BaseEntity{

    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;


    public Event getEvent() {
        return schedule.toEvent();
    }

    public User getAttendee() {
        return attendee;
    }

    public RequestStatus getStatus() {
        return requestStatus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public boolean isOverlapped(LocalDate date) {
        return this.schedule.isOverlapped(date);
    }


    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }
}
