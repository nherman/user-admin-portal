package com.nherman.useradminportal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.nherman.useradminportal.MongoTestConfiguration;
import com.nherman.useradminportal.dto.ActivityEventResponse;
import com.nherman.useradminportal.model.ActivityEvent;
import com.nherman.useradminportal.repository.ActivityEventRepository;

@DataMongoTest
@Import({MongoTestConfiguration.class, ActivityEventService.class})
class ActivityEventServiceTests {

	@Autowired
	private ActivityEventRepository activityEventRepository;

	@Autowired
	private ActivityEventService activityEventService;

	@BeforeEach
	void clearActivity() {
		activityEventRepository.deleteAll();
	}

	@Test
	void returnsRecentActivityNewestFirst() {
		activityEventRepository.save(new ActivityEvent(
				"USER_CREATED",
				1L,
				"ada@example.com",
				Instant.parse("2026-01-01T00:00:00Z")));
		activityEventRepository.save(new ActivityEvent(
				"USER_UPDATED",
				1L,
				"ada.updated@example.com",
				Instant.parse("2026-01-02T00:00:00Z")));

		assertThat(activityEventService.findRecent())
				.extracting(ActivityEventResponse::action)
				.containsExactly("USER_UPDATED", "USER_CREATED");
	}
}
