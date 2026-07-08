package com.nherman.useradminportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@NotBlank
		@Size(max = 100)
		String firstName,

		@NotBlank
		@Size(max = 100)
		String lastName,

		@NotBlank
		@Email
		@Size(max = 254)
		String email,

		@NotBlank
		@Size(max = 50)
		String role,

		@NotBlank
		@Size(max = 50)
		String status) {
}
