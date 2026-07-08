package com.nherman.useradminportal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.nherman.useradminportal.dto.UserRequest;
import com.nherman.useradminportal.dto.UserResponse;
import com.nherman.useradminportal.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	void findsAllUsers() throws Exception {
		when(userService.findAll()).thenReturn(List.of(response(1L, "ada@example.com")));

		mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].email").value("ada@example.com"));
	}

	@Test
	void findsUserById() throws Exception {
		when(userService.findById(1L)).thenReturn(response(1L, "ada@example.com"));

		mockMvc.perform(get("/api/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.email").value("ada@example.com"));
	}

	@Test
	void returnsNotFoundWhenUserDoesNotExist() throws Exception {
		when(userService.findById(99L))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		mockMvc.perform(get("/api/users/99"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createsUser() throws Exception {
		UserRequest request = request("ada@example.com");
		when(userService.create(any(UserRequest.class))).thenReturn(response(1L, "ada@example.com"));

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.email").value("ada@example.com"));
	}

	@Test
	void rejectsInvalidCreateRequest() throws Exception {
		UserRequest request = new UserRequest("", "Lovelace", "not-an-email", "ADMIN", "ACTIVE");

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updatesUser() throws Exception {
		UserRequest request = request("ada.updated@example.com");
		when(userService.update(any(Long.class), any(UserRequest.class)))
				.thenReturn(response(1L, "ada.updated@example.com"));

		mockMvc.perform(put("/api/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("ada.updated@example.com"));
	}

	@Test
	void deletesUser() throws Exception {
		mockMvc.perform(delete("/api/users/1"))
				.andExpect(status().isNoContent());

		verify(userService).delete(1L);
	}

	private UserRequest request(String email) {
		return new UserRequest("Ada", "Lovelace", email, "ADMIN", "ACTIVE");
	}

	private String json(UserRequest request) {
		return """
				{
				  "firstName": "%s",
				  "lastName": "%s",
				  "email": "%s",
				  "role": "%s",
				  "status": "%s"
				}
				""".formatted(
				request.firstName(),
				request.lastName(),
				request.email(),
				request.role(),
				request.status());
	}

	private UserResponse response(Long id, String email) {
		Instant now = Instant.parse("2026-01-01T00:00:00Z");
		return new UserResponse(id, "Ada", "Lovelace", email, "ADMIN", "ACTIVE", now, now);
	}
}
