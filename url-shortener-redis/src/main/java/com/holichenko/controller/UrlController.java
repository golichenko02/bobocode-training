package com.holichenko.controller;

import com.holichenko.dto.UrlInfoDto;
import com.holichenko.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/short")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<URI> shortenUrl(@RequestBody UrlInfoDto urlInfoDto) {
        return ResponseEntity.created(urlService.shortenUrl(urlInfoDto)).build();
    }

    @GetMapping("/{shortenUrlId}")
    public ResponseEntity<URI> findUrlById(@PathVariable String shortenUrlId) {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(urlService.findUrlById(shortenUrlId)).build();
    }
}
