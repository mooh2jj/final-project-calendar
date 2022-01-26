package com.dsg.fc.finalproject.api.service;

import com.dsg.fc.finalproject.api.dto.AuthUser;
import com.dsg.fc.finalproject.api.dto.NotificationCreateReq;
import com.dsg.fc.finalproject.core.domain.entity.Schedule;
import com.dsg.fc.finalproject.core.domain.entity.User;
import com.dsg.fc.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.dsg.fc.finalproject.core.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;


    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());
        final List<LocalDateTime> notifyAtList = notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach(notifyAt -> {
            final Schedule notificationSchedule = Schedule.notification(
                    notificationCreateReq.getTitle(),
                    notificationCreateReq.getNotifyAt(),
                    user
            );
            scheduleRepository.save(notificationSchedule);

        });

    }
}
