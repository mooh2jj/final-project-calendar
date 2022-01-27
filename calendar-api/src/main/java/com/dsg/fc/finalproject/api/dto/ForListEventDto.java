package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ForListEventDto implements ForListScheduleDto{
    private Long scheduleId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;
    private Long writerId;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.EVENT;
    }
}
