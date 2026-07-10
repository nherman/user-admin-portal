package com.nherman.useradminportal.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.nherman.useradminportal.MongoTestConfiguration;
import com.nherman.useradminportal.PostgresTestConfiguration;
import com.nherman.useradminportal.RabbitMqTestConfiguration;
import com.nherman.useradminportal.model.ActivityEvent;
import com.nherman.useradminportal.repository.ActivityEventRepository;

@SpringBootTest
@Import({PostgresTestConfiguration.class, MongoTestConfiguration.class, RabbitMqTestConfiguration.class})
class ActivityEventMessagingIntegrationTests {

	@Autowired
	private ActivityEventPublisher activityEventPublisher;

	@Autowired
	private ActivityEventRepository activityEventRepository;

	@BeforeEach
	void clearActivity() {
		activityEventRepository.deleteAll();
	}

	@Test
	void publishesActivityEventThroughRabbitMqAndWritesToMongo() throws InterruptedException {
		Instant occurredAt = Instant.parse("2026-01-01T00:00:00Z");

		activityEventPublisher.publish(new ActivityEventMessage(
				"USER_CREATED",
				1L,
				"ada@example.com",
				occurredAt));

		List<ActivityEvent> activityEvents = findActivityEvents();

		assertThat(activityEvents)
				.singleElement()
				.satisfies(activityEvent -> {
					assertThat(activityEvent.getAction()).isEqualTo("USER_CREATED");
					assertThat(activityEvent.getUserId()).isEqualTo(1L);
					assertThat(activityEvent.getEmail()).isEqualTo("ada@example.com");
					assertThat(activityEvent.getOccurredAt()).isEqualTo(occurredAt);
				});
	}

	private List<ActivityEvent> findActivityEvents() throws InterruptedException {
		for (int attempt = 0; attempt < 20; attempt++) {
			List<ActivityEvent> activityEvents = activityEventRepository.findAll();
			if (!activityEvents.isEmpty()) {
				return activityEvents;
			}
			Thread.sleep(250);
		}
		return activityEventRepository.findAll();
	}
}
