package com.nherman.useradminportal.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.nherman.useradminportal.MongoTestConfiguration;
import com.nherman.useradminportal.dto.HomepageContentResponse;
import com.nherman.useradminportal.model.HomepageContent;
import com.nherman.useradminportal.repository.HomepageContentRepository;

@DataMongoTest
@Import({MongoTestConfiguration.class, HomepageContentService.class})
class HomepageContentServiceTests {

	@Autowired
	private HomepageContentRepository homepageContentRepository;

	@Autowired
	private HomepageContentService homepageContentService;

	@Value("${homepage.default.title}")
	private String defaultTitle;

	@Value("${homepage.default.body}")
	private String defaultBody;

	@Value("${homepage.default.image-url}")
	private String defaultImageUrl;

	@BeforeEach
	void clearContent() {
		homepageContentRepository.deleteAll();
	}

	@Test
	void seedsDefaultHomepageContentWhenMissing() {
		homepageContentService.seedDefaultHomepageContent();

		assertThat(homepageContentRepository.count()).isEqualTo(1);
		HomepageContentResponse response = homepageContentService.getHomeContent();
		assertThat(response.title()).isEqualTo(defaultTitle);
		assertThat(response.body()).isEqualTo(defaultBody);
		assertThat(response.imageUrl()).isEqualTo(defaultImageUrl);
	}

	@Test
	void returnsExistingHomepageContent() {
		homepageContentRepository.save(new HomepageContent(
				"Custom Home",
				"Custom body",
				"/images/custom-home.png"));

		HomepageContentResponse response = homepageContentService.getHomeContent();

		assertThat(response.title()).isEqualTo("Custom Home");
		assertThat(response.body()).isEqualTo("Custom body");
		assertThat(response.imageUrl()).isEqualTo("/images/custom-home.png");
	}
}
