package com.mahendrafajar.dansmultipro.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthData {

    @NotEmpty(message = "Username is required")
    String username;

    @NotEmpty(message = "Password is required")
    String password;
}
