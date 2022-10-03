package com.example.urlshotener.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {

    @Id
    @GenericGenerator(name = "custom_generator", strategy = "com.example.urlshotener.entity.generator.ShortenedUrlIdGenerator")
    @GeneratedValue(generator = "custom_generator")
    private String id;

    @Column(nullable = false, updatable = false, unique = true)
    private String originalUrl;

    private String title;

    @Column(updatable = false, nullable = false, insertable = false)
    private LocalDateTime createdAt;
}
