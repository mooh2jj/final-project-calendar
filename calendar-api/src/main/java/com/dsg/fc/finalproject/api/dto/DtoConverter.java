package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.domain.entity.Schedule;
import com.dsg.fc.finalproject.core.exception.CalendarException;
import com.dsg.fc.finalproject.core.exception.ErrorCode;

public abstract class DtoConverter {
    public static ForListScheduleDto fromSchedule(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case EVENT:
                return ForListEventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return ForListTaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return ForListNotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }
}
