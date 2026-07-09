package com.nherman.useradminportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nherman.useradminportal.dto.HomepageContentResponse;
import com.nherman.useradminportal.service.HomepageContentService;

@RestController
@RequestMapping("/api/content")
public class HomepageContentController {

	private final HomepageContentService homepageContentService;

	public HomepageContentController(HomepageContentService homepageContentService) {
		this.homepageContentService = homepageContentService;
	}

	@GetMapping("/home")
	public HomepageContentResponse getHomeContent() {
		return homepageContentService.getHomeContent();
	}
}
