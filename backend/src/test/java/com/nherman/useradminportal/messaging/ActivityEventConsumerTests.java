package com.nherman.useradminportal.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.nherman.useradminportal.MongoTestConfiguration;
import com.nherman.useradminportal.model.ActivityEvent;
import com.nherman.useradminportal.repository.ActivityEventRepository;

@DataMongoTest
@Import({MongoTestConfiguration.class, ActivityEventConsumer.class})
class ActivityEventConsumerTests {

	@Autowired
	private ActivityEventRepository activityEventRepository;

	@Autowired
	private ActivityEventConsumer activityEventConsumer;

	@BeforeEach
	void clearActivity() {
		activityEventRepository.deleteAll();
	}

	@Test
	void writesActivityEventsToMongo() {
		Instant occurredAt = Instant.parse("2026-01-01T00:00:00Z");

		activityEventConsumer.handle(new ActivityEventMessage(
				"USER_CREATED",
				1L,
				"ada@example.com",
				occurredAt));

		assertThat(activityEventRepository.findAll())
				.singleElement()
				.satisfies(activityEvent -> {
					assertThat(activityEvent.getAction()).isEqualTo("USER_CREATED");
					assertThat(activityEvent.getUserId()).isEqualTo(1L);
					assertThat(activityEvent.getEmail()).isEqualTo("ada@example.com");
					assertThat(activityEvent.getOccurredAt()).isEqualTo(occurredAt);
				});
	}
}
