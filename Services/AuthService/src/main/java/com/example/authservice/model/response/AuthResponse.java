package com.example.authservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("token")
    String token;

    @JsonProperty("expired_time")
    Long expiredTime;
}
