package com.nherman.useradminportal.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.nherman.useradminportal.config.RabbitMqConfiguration;
import com.nherman.useradminportal.model.ActivityEvent;
import com.nherman.useradminportal.repository.ActivityEventRepository;

@Service
public class ActivityEventConsumer {

	private final ActivityEventRepository activityEventRepository;

	public ActivityEventConsumer(ActivityEventRepository activityEventRepository) {
		this.activityEventRepository = activityEventRepository;
	}

	@RabbitListener(queues = RabbitMqConfiguration.ACTIVITY_EVENTS_QUEUE)
	public void handle(ActivityEventMessage activityEvent) {
		activityEventRepository.save(new ActivityEvent(
				activityEvent.action(),
				activityEvent.userId(),
				activityEvent.email(),
				activityEvent.occurredAt()));
	}
}
