package com.dsg.fc.finalproject.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginReq {

    private String email;
    private String password;
}
