package com.holichenko.service;

import com.holichenko.dto.UrlInfoDto;
import com.holichenko.model.ShortenedUrl;
import com.holichenko.repository.ShortenedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final ShortenedUrlRepository urlRepository;

    public URI findUrlById(String shortenUrlId) {
        return urlRepository.findById(shortenUrlId)
                .map(ShortenedUrl::getOriginalUrl)
                .map(URI::create)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not found URL by id %s".formatted(shortenUrlId)));
    }

    public URI shortenUrl(UrlInfoDto urlInfoDto) {
        return urlRepository.findByOriginalUrlAndTitle(urlInfoDto.url(), urlInfoDto.title())
                .map(ShortenedUrl::getId)
                .map(URI::create)
                .orElseGet(() -> createShortenedUrl(urlInfoDto));
    }

    private URI createShortenedUrl(UrlInfoDto urlInfoDto) {
        ShortenedUrl shortenedUrl = buildShortenedUrl(urlInfoDto);
        urlRepository.save(shortenedUrl);
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(shortenedUrl.getId())
                .toUri();
    }

    private ShortenedUrl buildShortenedUrl(UrlInfoDto dto) {
        return ShortenedUrl.builder()
                .id(RandomStringUtils.random(10, true, true))
                .originalUrl(dto.url())
                .title(dto.title())
                .createdAt(LocalDateTime.now(Clock.systemUTC()))
                .build();
    }
}
