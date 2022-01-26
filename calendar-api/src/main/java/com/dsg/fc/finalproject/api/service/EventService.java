package com.dsg.fc.finalproject.api.service;

import com.dsg.fc.finalproject.api.dto.AuthUser;
import com.dsg.fc.finalproject.api.dto.EventCreateReq;
import com.dsg.fc.finalproject.core.domain.RequestStatus;
import com.dsg.fc.finalproject.core.domain.entity.Engagement;
import com.dsg.fc.finalproject.core.domain.entity.Schedule;
import com.dsg.fc.finalproject.core.domain.entity.User;
import com.dsg.fc.finalproject.core.domain.entity.repository.EngagementRepository;
import com.dsg.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.dsg.fc.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EmailService emailService;

    private final EngagementRepository engagementRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

//    @Transactional
//    public void create(EventCreateReq eventCreateReq, AuthUser authUser) {
//        final List<Engagement> engagementList = engagementRepository.findAll();
//
//        if (engagementList.stream()
//                .anyMatch(e -> eventCreateReq.getAttendeeIds().contains(e.getAttendee().getId())
//                && e.getStatus() == RequestStatus.ACCEPTED
//                && e.getEvent().isOverlapped(
//                        eventCreateReq.getStartAt(),
//                        eventCreateReq.getEndAt()
//                        ))
//        ) {
//            throw new RuntimeException("cannot make engagement. period overlapped.");
//        }
//        final Schedule eventSchedule = Schedule.event(
//                eventCreateReq.getTitle(),
//                eventCreateReq.getDescription(),
//                eventCreateReq.getStartAt(),
//                eventCreateReq.getEndAt(),
//                userService.findByUserId(authUser.getId())
//        );
//
//        scheduleRepository.save(eventSchedule);
//        eventCreateReq.getAttendeeIds()
//                .forEach(atId -> {
//                    final User attendee = userService.findByUserId(atId);
//                    final Engagement engagement = Engagement.builder()
//                            .schedule(eventSchedule)
//                            .requestStatus(RequestStatus.REQUESTED)
//                            .attendee(attendee)
//                            .build();
//                    engagementRepository.save(engagement);
//                    emailService.sendEngagement(engagement);
//                });
//
//    }

    @Transactional
    public void create(EventCreateReq req, AuthUser authUser) {
        // attendees 의 스케쥴 시간과 겹치지 않는지?
        final List<Engagement> engagementList =
                engagementRepository.findAllByAttendeeIdInAndSchedule_EndAtAfter(req.getAttendeeIds(),
                        req.getStartAt());
        if (engagementList
                .stream()
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt())
                        && e.getStatus() == RequestStatus.ACCEPTED)) {
            throw new RuntimeException("cannot make engagement. period overlapped.");
        }
        final Schedule eventSchedule = Schedule.event(req.getTitle(), req.getDescription(), req.getStartAt(), req.getEndAt(), userService.findByUserId(authUser.getId()));
        scheduleRepository.save(eventSchedule);
        req.getAttendeeIds().stream()
                .map(userService::findByUserId)
                .forEach(user -> {
                    final Engagement e = engagementRepository.save(new Engagement(eventSchedule, user));
                    emailService.sendEngagement(e);
                });
    }
}
