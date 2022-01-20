package com.dsg.fc.finalproject.core.domain.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@NoArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User extends BaseEntity{


    private String name;
    private String email;
    private String password;
    private LocalDate birthday;


    public User(String name, String email, String password, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }
}
