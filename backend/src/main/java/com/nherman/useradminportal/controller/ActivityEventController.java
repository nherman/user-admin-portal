package com.nherman.useradminportal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nherman.useradminportal.dto.ActivityEventResponse;
import com.nherman.useradminportal.service.ActivityEventService;

@RestController
@RequestMapping("/api/activity")
public class ActivityEventController {

	private final ActivityEventService activityEventService;

	public ActivityEventController(ActivityEventService activityEventService) {
		this.activityEventService = activityEventService;
	}

	@GetMapping
	public List<ActivityEventResponse> findRecent() {
		return activityEventService.findRecent();
	}
}
