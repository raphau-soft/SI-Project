package com.example.demo.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenResponse {
    @JsonProperty
    String jwt;

    public RefreshTokenResponse(String jwt) {
        this.jwt = jwt;
    }
}