package com.holichenko.repository;

import com.holichenko.model.ShortenedUrl;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrl, String> {
     Optional<ShortenedUrl> findByOriginalUrlAndTitle(String originalUrl, String title);
}
