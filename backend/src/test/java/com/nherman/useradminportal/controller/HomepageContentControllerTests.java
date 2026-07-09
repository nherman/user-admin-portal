package com.nherman.useradminportal.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nherman.useradminportal.dto.HomepageContentResponse;
import com.nherman.useradminportal.service.HomepageContentService;

@WebMvcTest(HomepageContentController.class)
class HomepageContentControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private HomepageContentService homepageContentService;

	@Value("${homepage.default.title}")
	private String defaultTitle;

	@Value("${homepage.default.body}")
	private String defaultBody;

	@Value("${homepage.default.image-url}")
	private String defaultImageUrl;

	@Test
	void getsHomepageContent() throws Exception {
		when(homepageContentService.getHomeContent())
				.thenReturn(new HomepageContentResponse(defaultTitle, defaultBody, defaultImageUrl));

		mockMvc.perform(get("/api/content/home"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(defaultTitle))
				.andExpect(jsonPath("$.body").value(defaultBody))
				.andExpect(jsonPath("$.imageUrl").value(defaultImageUrl));
	}
}
