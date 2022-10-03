package com.example.urlshotener.repository;

import com.example.urlshotener.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {

    @Query("select su from ShortenedUrl su where su.id = :id")
    Optional<ShortenedUrl> findById(String id);
}
