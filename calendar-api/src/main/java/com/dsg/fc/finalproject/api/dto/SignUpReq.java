package com.dsg.fc.finalproject.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class SignUpReq {

    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
}
