package com.nherman.useradminportal.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("activity_events")
public class ActivityEvent {

	@Id
	private String id;

	private String action;

	private Long userId;

	private String email;

	private Instant occurredAt;

	public ActivityEvent() {
	}

	public ActivityEvent(String action, Long userId, String email, Instant occurredAt) {
		this.action = action;
		this.userId = userId;
		this.email = email;
		this.occurredAt = occurredAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getOccurredAt() {
		return occurredAt;
	}

	public void setOccurredAt(Instant occurredAt) {
		this.occurredAt = occurredAt;
	}
}
