package com.nherman.useradminportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("homepage_content")
public class HomepageContent {

	@Id
	private String id;

	private String title;

	private String body;

	private String imageUrl;

	public HomepageContent() {
	}

	public HomepageContent(String title, String body, String imageUrl) {
		this.title = title;
		this.body = body;
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
