package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ForListTaskDto implements ForListScheduleDto {
    private Long scheduleId;
    private Long writerId;
    private String title;
    private String description;
    private LocalDateTime taskAt;

    @Override
    public ScheduleType getScheduleType() {
        return ScheduleType.TASK;
    }
}
