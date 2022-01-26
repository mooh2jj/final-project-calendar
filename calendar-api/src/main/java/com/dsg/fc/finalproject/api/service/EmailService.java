package com.dsg.fc.finalproject.api.service;

import com.dsg.fc.finalproject.core.domain.entity.Engagement;

public interface EmailService {

    void sendEngagement(Engagement engagement);
}
