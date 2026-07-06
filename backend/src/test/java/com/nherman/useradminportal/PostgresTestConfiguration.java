package com.nherman.useradminportal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class PostgresTestConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer(
			@Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password) {
		return new PostgreSQLContainer<>("postgres:15-alpine")
				.withDatabaseName("user_admin")
				.withUsername(username)
				.withPassword(password);
	}
}
