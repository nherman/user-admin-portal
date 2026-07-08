package com.nherman.useradminportal.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nherman.useradminportal.dto.UserRequest;
import com.nherman.useradminportal.dto.UserResponse;
import com.nherman.useradminportal.model.User;
import com.nherman.useradminportal.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
		return toResponse(userRepository.save(user));
	}

	public UserResponse update(Long id, UserRequest request) {
		User user = findUser(id);
		applyRequest(user, request);
		return toResponse(userRepository.save(user));
	}

	public void delete(Long id) {
		User user = findUser(id);
		userRepository.delete(user);
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
