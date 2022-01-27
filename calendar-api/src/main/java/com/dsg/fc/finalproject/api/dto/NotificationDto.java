package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.domain.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto implements ForListScheduleDto {

    private Long scheduleId;
    private Long writerId;
    private String title;
    private LocalDateTime notifyAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
