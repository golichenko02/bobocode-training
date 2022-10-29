package com.holichenko.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("shortened_urls")
public class ShortenedUrl {

    @Id
    private String id;
    @Indexed
    private String originalUrl;
    private String title;
    private LocalDateTime createdAt;
}
