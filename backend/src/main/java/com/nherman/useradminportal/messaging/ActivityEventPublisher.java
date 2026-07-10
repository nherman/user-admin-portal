package com.nherman.useradminportal.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.nherman.useradminportal.config.RabbitMqConfiguration;

@Service
public class ActivityEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public ActivityEventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publish(ActivityEventMessage activityEvent) {
		rabbitTemplate.convertAndSend(RabbitMqConfiguration.ACTIVITY_EVENTS_QUEUE, activityEvent);
	}
}
