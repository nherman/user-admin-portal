package com.nherman.useradminportal.dto;

import java.time.Instant;

public record UserResponse(
		Long id,
		String firstName,
		String lastName,
		String email,
		String role,
		String status,
		Instant createdAt,
		Instant modifiedAt) {
}
