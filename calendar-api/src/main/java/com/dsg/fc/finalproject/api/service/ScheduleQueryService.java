package com.dsg.fc.finalproject.api.service;

import com.dsg.fc.finalproject.api.dto.AuthUser;
import com.dsg.fc.finalproject.api.dto.DtoConverter;
import com.dsg.fc.finalproject.api.dto.ForListScheduleDto;
import com.dsg.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.dsg.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.dsg.fc.finalproject.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
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
        final Period period = Period.of(date, date);

        return getScheduleByPeriod(authUser, period);
    }

    public List<ForListScheduleDto> getSchedulesByWeek(LocalDate startOfWeek, AuthUser authUser) {
        final Period period = Period.of(startOfWeek, startOfWeek.plusDays(6));
        return getScheduleByPeriod(authUser, period);
    }

    public List<ForListScheduleDto> getSchedulesByMonth(YearMonth yearMonth, AuthUser authUser) {
        final Period period = Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
        return getScheduleByPeriod(authUser, period);
    }

    private List<ForListScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                scheduleRepository
                        .findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(period))
                        .map(DtoConverter::fromSchedule),
                engagementRepository
                        .findAllByAttendeeId(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(period))
                        .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()))
        ).collect(toList());
    }
}
