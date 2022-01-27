package com.dsg.fc.finalproject.api.service;

import com.dsg.fc.finalproject.api.dto.AuthUser;
import com.dsg.fc.finalproject.api.dto.DtoConverter;
import com.dsg.fc.finalproject.api.dto.ForListScheduleDto;
import com.dsg.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.dsg.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryService {
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ForListScheduleDto> getSchedulesByDay(LocalDate date, AuthUser authUser) {
        return Stream.concat(
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(date))
                        .map(schedule -> DtoConverter.toForListDto(schedule)),
                engagementRepository
                        .findAllByAttendeeId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(date))
                        .map(engagement -> DtoConverter.toForListDto(engagement.getSchedule()))
        ).collect(toList());
    }

}
