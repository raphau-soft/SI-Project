package com.example.demo.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResponse {
    @JsonProperty
    String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
