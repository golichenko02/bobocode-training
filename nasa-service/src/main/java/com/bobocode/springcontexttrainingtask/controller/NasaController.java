package com.bobocode.springcontexttrainingtask.controller;

import com.bobocode.springcontexttrainingtask.service.NasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NasaController {
    private final NasaService nasaService;

    @GetMapping("/pictures/{sol}/largest")
    public ResponseEntity<?> findLargestPicture(@PathVariable Long sol) {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(nasaService.findLargestNasaPicture(sol))
                .build();
    }
}
