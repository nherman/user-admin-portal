package com.nherman.useradminportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nherman.useradminportal.dto.ActivityEventResponse;
import com.nherman.useradminportal.model.ActivityEvent;
import com.nherman.useradminportal.repository.ActivityEventRepository;

@Service
public class ActivityEventService {

	private final ActivityEventRepository activityEventRepository;

	public ActivityEventService(ActivityEventRepository activityEventRepository) {
		this.activityEventRepository = activityEventRepository;
	}

	public List<ActivityEventResponse> findRecent() {
		return activityEventRepository.findTop50ByOrderByOccurredAtDesc()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	private ActivityEventResponse toResponse(ActivityEvent activityEvent) {
		return new ActivityEventResponse(
				activityEvent.getId(),
				activityEvent.getAction(),
				activityEvent.getUserId(),
				activityEvent.getEmail(),
				activityEvent.getOccurredAt());
	}
}
