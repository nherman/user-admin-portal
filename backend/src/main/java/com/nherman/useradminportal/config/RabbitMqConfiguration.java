package com.nherman.useradminportal.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration {

	public static final String ACTIVITY_EVENTS_QUEUE = "activity.events";

	@Bean
	Queue activityEventsQueue() {
		return QueueBuilder.durable(ACTIVITY_EVENTS_QUEUE).build();
	}

	@Bean
	MessageConverter messageConverter() {
		return new JacksonJsonMessageConverter("com.nherman.useradminportal.messaging");
	}
}
