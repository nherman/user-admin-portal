package com.nherman.useradminportal.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nherman.useradminportal.dto.UserRequest;
import com.nherman.useradminportal.dto.UserResponse;
import com.nherman.useradminportal.messaging.ActivityEventMessage;
import com.nherman.useradminportal.messaging.ActivityEventPublisher;
import com.nherman.useradminportal.model.User;
import com.nherman.useradminportal.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ActivityEventPublisher activityEventPublisher;

	public UserService(UserRepository userRepository, ActivityEventPublisher activityEventPublisher) {
		this.userRepository = userRepository;
		this.activityEventPublisher = activityEventPublisher;
	}

	public List<UserResponse> findAll() {
		return userRepository.findAll()
				.stream()
				.map(this::toResponse)
				.toList();
	}

	public UserResponse findById(Long id) {
		return toResponse(findUser(id));
	}

	public UserResponse create(UserRequest request) {
		User user = new User();
		applyRequest(user, request);
		User saved = userRepository.save(user);
		publishActivity("USER_CREATED", saved);
		return toResponse(saved);
	}

	public UserResponse update(Long id, UserRequest request) {
		User user = findUser(id);
		applyRequest(user, request);
		User saved = userRepository.save(user);
		publishActivity("USER_UPDATED", saved);
		return toResponse(saved);
	}

	public void delete(Long id) {
		User user = findUser(id);
		userRepository.delete(user);
		publishActivity("USER_DELETED", user);
	}

	private User findUser(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	private void applyRequest(User user, UserRequest request) {
		user.setFirstName(request.firstName());
		user.setLastName(request.lastName());
		user.setEmail(request.email());
		user.setRole(request.role());
		user.setStatus(request.status());
	}

	private void publishActivity(String action, User user) {
		activityEventPublisher.publish(new ActivityEventMessage(
				action,
				user.getId(),
				user.getEmail(),
				Instant.now()));
	}

	private UserResponse toResponse(User user) {
		return new UserResponse(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getRole(),
				user.getStatus(),
				user.getCreatedAt(),
				user.getModifiedAt());
	}
}
