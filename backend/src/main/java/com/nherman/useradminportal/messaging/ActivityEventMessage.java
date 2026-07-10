package com.nherman.useradminportal.messaging;

import java.io.Serializable;
import java.time.Instant;

public record ActivityEventMessage(
		String action,
		Long userId,
		String email,
		Instant occurredAt) implements Serializable {
}
