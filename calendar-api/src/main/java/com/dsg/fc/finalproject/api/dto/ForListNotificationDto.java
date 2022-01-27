package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ForListNotificationDto implements ForListScheduleDto {
    private Long scheduleId;
    private Long writerId;
    private String title;
    private LocalDateTime notifyAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.NOTIFICATION;
    }
}
