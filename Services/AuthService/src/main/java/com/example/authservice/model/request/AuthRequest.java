package com.example.authservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotNull
    @JsonProperty("password")
    private String password;
}
