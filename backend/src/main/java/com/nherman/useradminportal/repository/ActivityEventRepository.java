package com.nherman.useradminportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nherman.useradminportal.model.ActivityEvent;

public interface ActivityEventRepository extends MongoRepository<ActivityEvent, String> {

	List<ActivityEvent> findTop50ByOrderByOccurredAtDesc();
}
