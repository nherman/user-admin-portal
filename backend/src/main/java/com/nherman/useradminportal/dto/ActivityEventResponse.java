package com.nherman.useradminportal.dto;

import java.time.Instant;

public record ActivityEventResponse(
		String id,
		String action,
		Long userId,
		String email,
		Instant occurredAt) {
}
