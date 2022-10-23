package com.example.urlshotener.controller;


import com.example.urlshotener.dto.ShortenedUrlDto;
import com.example.urlshotener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
@RequestMapping("/short")
@RequiredArgsConstructor
public class ShortenerUrlController {
    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<?> createShortenedUrl(@RequestBody ShortenedUrlDto request) {
        return ResponseEntity.created(urlShortenerService.buildShortenedURI(request.url(), request.title())).build();
    }

    @GetMapping("/{shortenUrlId}")
    public ResponseEntity<?> findOriginalURL(@PathVariable String shortenUrlId) {
        URI originalUrlById = urlShortenerService.getOriginalUrlById(shortenUrlId);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(originalUrlById).build();
    }

}
