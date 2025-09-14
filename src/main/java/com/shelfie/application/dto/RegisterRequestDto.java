package com.shelfie.application.dto;

public record RegisterRequestDto(
        String username,
        String email,
        String firstName,
        String lastName,
        String password
) {}