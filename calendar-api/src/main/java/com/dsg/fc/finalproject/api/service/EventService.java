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

    @Transactional
    public void create(EventCreateReq eventCreateReq, AuthUser authUser) {
        // 이벤트 참여자의 다른 이벤트와 중복이 되면 안된다.
        // 추가로 다른 회의를 등록할 수 없음
        // 추가로 이메일 발송
        final List<Engagement> engagementList = engagementRepository.findAll();    // TODO findAall 개선..
        // findAll : 절대 실무에선 이렇게 무식하게 다 가져오지 않음

        if (engagementList.stream()
                .anyMatch(e -> eventCreateReq.getAttendeeIds().contains(e.getAttendee().getId())
                && e.getStatus() == RequestStatus.ACCEPTED
                && e.getEvent().isOverlapped(
                        eventCreateReq.getStartAt(),
                        eventCreateReq.getEndAt()
                        ))
        ) {
            throw new RuntimeException("cannot make engagement. period overlapped.");
        }
        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.findByUserId(authUser.getId())
        );

        scheduleRepository.save(eventSchedule);
        eventCreateReq.getAttendeeIds()
                .forEach(atId -> {
                    final User attendee = userService.findByUserId(atId);
                    System.out.println("########### attendee" + attendee.toString());
                    final Engagement engagement = Engagement.builder()
                            .schedule(eventSchedule)
                            .requestStatus(RequestStatus.REQUESTED)
                            .attendee(attendee)
                            .build();
                    System.out.println("########### engagement" + engagement.toString());
                    engagementRepository.save(engagement);
                    emailService.sendEngagement(engagement);

                });

    }
}
