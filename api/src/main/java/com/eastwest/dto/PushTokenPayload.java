package com.eastwest.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class PushTokenPayload {
    @NotNull
    private String token;
}

