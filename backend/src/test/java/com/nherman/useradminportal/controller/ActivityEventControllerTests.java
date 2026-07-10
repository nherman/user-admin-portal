package com.nherman.useradminportal.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nherman.useradminportal.dto.ActivityEventResponse;
import com.nherman.useradminportal.service.ActivityEventService;

@WebMvcTest(ActivityEventController.class)
class ActivityEventControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ActivityEventService activityEventService;

	@Test
	void findsRecentActivity() throws Exception {
		when(activityEventService.findRecent()).thenReturn(List.of(new ActivityEventResponse(
				"activity-id",
				"USER_CREATED",
				1L,
				"ada@example.com",
				Instant.parse("2026-01-01T00:00:00Z"))));

		mockMvc.perform(get("/api/activity"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value("activity-id"))
				.andExpect(jsonPath("$[0].action").value("USER_CREATED"))
				.andExpect(jsonPath("$[0].userId").value(1))
				.andExpect(jsonPath("$[0].email").value("ada@example.com"));
	}
}
