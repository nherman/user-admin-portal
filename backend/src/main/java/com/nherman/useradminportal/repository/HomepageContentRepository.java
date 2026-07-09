package com.nherman.useradminportal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nherman.useradminportal.model.HomepageContent;

public interface HomepageContentRepository extends MongoRepository<HomepageContent, String> {

	Optional<HomepageContent> findFirstByOrderByIdAsc();
}
