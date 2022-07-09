package com.bobocode.springcontexttrainingtask.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class NasaService {

    private final String API_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    private final String API_KEY = "DEMO_KEY";
    private final RestTemplate restTemplate;

    public URI findLargestNasaPicture(Long sol) {
        Optional<JsonNode> response = Optional.ofNullable(restTemplate.getForObject(prepareURI(sol), JsonNode.class));

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
