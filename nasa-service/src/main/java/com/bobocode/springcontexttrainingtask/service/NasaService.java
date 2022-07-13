package com.bobocode.springcontexttrainingtask.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NasaService {

    @Value("${nasa.api.url}")
    private String API_URL;
    @Value("${nasa.api.key}")
    private String API_KEY;
    private final RestTemplate restTemplate;

    @Cacheable("picture")
    public URI findLargestNasaPicture(Long sol) {
        Optional<JsonNode> response = Optional.ofNullable(restTemplate.getForObject(prepareURI(sol), JsonNode.class));
        log.info("Calling nasa by sol: {}", sol);
        Pair<URI, Long> result = response.map(jsonNode -> jsonNode.findValues("img_src").parallelStream()
                        .map(JsonNode::asText)
                        .map(URI::create)
                        .collect(toMap(Function.identity(), this::getSize)).entrySet().parallelStream()
                        .max(Map.Entry.comparingByValue())
                        .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                        .orElse(Pair.of(URI.create("NOT_FOUND"), -1L)))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return result.getKey();
    }

    @CacheEvict(value = "picture", allEntries = true)
    public void clearCache(){
        log.debug("Clearing picture cache...");
    }

    private Long getSize(URI url) {
        URI location = restTemplate.headForHeaders(url).getLocation();
        return Optional.ofNullable(location)
                .map(uri -> restTemplate.headForHeaders(location).getContentLength())
                .orElseThrow(() -> new IllegalArgumentException("Location header is empty!"));
    }

    private URI prepareURI(Long sol) {
        return UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("sol", sol)
                .queryParam("api_key", API_KEY)
                .build()
                .toUri();
    }
}
