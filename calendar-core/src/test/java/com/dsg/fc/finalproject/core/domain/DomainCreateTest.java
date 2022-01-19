package com.dsg.fc.finalproject.core.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainCreateTest {


    @Test
    void eventCreate() {
        final User writer = new User("dsgwriter", "dsg@gmail.com", "pwd", LocalDate.now(), LocalDateTime.now());
        final User attendee = new User("dsgattendee", "dsg@gmail.com", "pwd", LocalDate.now(), LocalDateTime.now());
        final Event event = new Event(
            LocalDateTime.now(),
            LocalDateTime.now(),
            "title", "desc",
            writer,
            LocalDateTime.now()
        );

        event.addEnagagement(new Engagment(event, attendee, LocalDateTime.now(), RequestStatus.REQUESTED));

        assertEquals(event.getEngagments().get(0).getEvent().getWriter().getName(), "dsgwriter");
    }
}