package com.dsg.fc.finalproject.api.dto;

import com.dsg.fc.finalproject.core.exception.CalendarException;
import com.dsg.fc.finalproject.core.exception.ErrorCode;
import com.dsg.fc.finalproject.core.util.TimeUnit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


@Data
public class NotificationCreateReq {
    @NotBlank
    private String title;
    @NotNull
    private LocalDateTime notifyAt;
    private RepeatInfo repeatInfo;


    public List<LocalDateTime> getRepeatTimes() {
        if (repeatInfo == null) {
            return Collections.singletonList(notifyAt);
        }
        return IntStream.range(0, repeatInfo.times)
                .mapToObj(i -> {
                    long increment = (long) repeatInfo.inteval.intervalValue * i;
                    switch (repeatInfo.inteval.timeUnit) {
                        case DAY:
                            return notifyAt.plusDays(increment);
                        case WEEK:
                            return notifyAt.plusWeeks(increment);
                        case MONTH:
                            return notifyAt.plusMonths(increment);
                        case YEAR:
                            return notifyAt.plusYears(increment);
                        default:
                            throw new CalendarException(ErrorCode.BAD_REQUEST);
                    }
                })
                .collect(toList());
    }

    @Data
    public static class RepeatInfo {
        private Inteval inteval;
        private int times;
    }

    @Data
    public static class Inteval {
        private int intervalValue;
        private TimeUnit timeUnit;
    }

}
