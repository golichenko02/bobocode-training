package com.example.urlshotener.service;

import com.example.urlshotener.entity.ShortenedUrl;
import com.example.urlshotener.repository.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortenedUrlRepository urlRepository;

    @Retryable
    public URI buildShortenedURI(String originalUrl, String title) {
        ShortenedUrl shortenedUrl = buildEntity(originalUrl, title);
        urlRepository.save(shortenedUrl);
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(shortenedUrl.getId())
                .toUri();
    }

    @Cacheable("urls")
    @Transactional(readOnly = true)
    public URI getOriginalUrlById(String shortenUrlId) {
        return urlRepository.findById(shortenUrlId)
                .map(ShortenedUrl::getOriginalUrl)
                .map(URI::create)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    private ShortenedUrl buildEntity(String originalUrl, String title) {
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setTitle(title);
        return shortenedUrl;
    }
}
