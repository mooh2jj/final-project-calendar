package com.dsg.fc.finalproject.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class LoginReq {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
