package com.nherman.useradminportal.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nherman.useradminportal.dto.UserRequest;
import com.nherman.useradminportal.messaging.ActivityEventMessage;
import com.nherman.useradminportal.messaging.ActivityEventPublisher;
import com.nherman.useradminportal.model.User;
import com.nherman.useradminportal.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ActivityEventPublisher activityEventPublisher;

	@InjectMocks
	private UserService userService;

	@Test
	void publishesActivityWhenUserIsCreated() {
		User saved = user(1L, "ada@example.com");
		when(userRepository.save(any(User.class))).thenReturn(saved);

		userService.create(request("ada@example.com"));

		assertActivityWasPublished("USER_CREATED", 1L, "ada@example.com");
	}

	@Test
	void publishesActivityWhenUserIsUpdated() {
		User existing = user(1L, "ada@example.com");
		User saved = user(1L, "ada.updated@example.com");
		when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(userRepository.save(existing)).thenReturn(saved);

		userService.update(1L, request("ada.updated@example.com"));

		assertActivityWasPublished("USER_UPDATED", 1L, "ada.updated@example.com");
	}

	@Test
	void publishesActivityWhenUserIsDeleted() {
		User existing = user(1L, "ada@example.com");
		when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

		userService.delete(1L);

		assertActivityWasPublished("USER_DELETED", 1L, "ada@example.com");
	}

	private void assertActivityWasPublished(String action, Long userId, String email) {
		ArgumentCaptor<ActivityEventMessage> captor = ArgumentCaptor.forClass(ActivityEventMessage.class);
		verify(activityEventPublisher).publish(captor.capture());

		ActivityEventMessage activityEvent = captor.getValue();
		assertThat(activityEvent.action()).isEqualTo(action);
		assertThat(activityEvent.userId()).isEqualTo(userId);
		assertThat(activityEvent.email()).isEqualTo(email);
		assertThat(activityEvent.occurredAt()).isNotNull();
	}

	private UserRequest request(String email) {
		return new UserRequest("Ada", "Lovelace", email, "ADMIN", "ACTIVE");
	}

	private User user(Long id, String email) {
		User user = new User("Ada", "Lovelace", email, "ADMIN", "ACTIVE");
		user.setId(id);
		return user;
	}
}
