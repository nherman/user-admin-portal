package com.nherman.useradminportal.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.nherman.useradminportal.model.User;

@SpringBootTest
@Transactional
class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void savesUserAndFindsByEmail() {
		String email = "ada.lovelace.%s@example.com".formatted(UUID.randomUUID());
		User user = new User("Ada", "Lovelace", email, "ADMIN", "ACTIVE");

		User saved = userRepository.saveAndFlush(user);

		Optional<User> found = userRepository.findByEmail(email);

		assertThat(found).isPresent();
		assertThat(found.get().getId()).isEqualTo(saved.getId());
		assertThat(found.get().getEmail()).isEqualTo(email);
	}
}
