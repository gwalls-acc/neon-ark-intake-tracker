package org.example.neonarkintaketracker.dto;

import java.util.List;

public record UserAdminResponse(
        String fullName,
        String email,
        String phone,
        List<String> roles
) {}