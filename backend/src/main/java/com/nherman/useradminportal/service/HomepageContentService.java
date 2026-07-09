package com.nherman.useradminportal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.nherman.useradminportal.dto.HomepageContentResponse;
import com.nherman.useradminportal.model.HomepageContent;
import com.nherman.useradminportal.repository.HomepageContentRepository;

@Service
public class HomepageContentService {

	private final HomepageContentRepository homepageContentRepository;

	@Value("${homepage.default.title}")
	private String defaultTitle;

	@Value("${homepage.default.body}")
	private String defaultBody;

	@Value("${homepage.default.image-url}")
	private String defaultImageUrl;

	public HomepageContentService(HomepageContentRepository homepageContentRepository) {
		this.homepageContentRepository = homepageContentRepository;
	}

	public HomepageContentResponse getHomeContent() {
		return homepageContentRepository.findFirstByOrderByIdAsc()
				.map(this::toResponse)
				.orElseGet(() -> toResponse(createDefaultContent()));
	}

	@EventListener(ApplicationReadyEvent.class)
	public void seedDefaultHomepageContent() {
		if (homepageContentRepository.count() == 0) {
			createDefaultContent();
		}
	}

	private HomepageContent createDefaultContent() {
		return homepageContentRepository.save(new HomepageContent(defaultTitle, defaultBody, defaultImageUrl));
	}

	private HomepageContentResponse toResponse(HomepageContent homepageContent) {
		return new HomepageContentResponse(
				homepageContent.getTitle(),
				homepageContent.getBody(),
				homepageContent.getImageUrl());
	}
}
