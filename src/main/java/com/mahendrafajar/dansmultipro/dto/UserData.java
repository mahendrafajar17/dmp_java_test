package com.mahendrafajar.dansmultipro.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserData {
    @NotEmpty(message = "Name is required")
    String name;

    @NotEmpty(message = "Username is required")
    String username;

    @NotEmpty(message = "Password is required")
    String password;
}
