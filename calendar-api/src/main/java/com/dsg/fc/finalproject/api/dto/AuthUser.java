package com.dsg.fc.finalproject.api.dto;

import lombok.Getter;

@Getter
public class AuthUser {
    private Long id;

    private AuthUser(Long id) {
        this.id = id;
    }

    public static AuthUser of(Long id) {
        return new AuthUser(id);
    }
}
