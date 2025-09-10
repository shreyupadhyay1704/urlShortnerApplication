package com.intellias.urlshortener.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public final class UrlMapping {
    private final String shortCode;
    private final String fullUrl;
    private final LocalDateTime createdAt;

    public UrlMapping(String shortCode, String fullUrl, LocalDateTime createdAt) {
        this.shortCode = validateShortCode(shortCode);
        this.fullUrl = validateFullUrl(fullUrl);
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public UrlMapping(String shortCode, String fullUrl) {
        this(shortCode, fullUrl, LocalDateTime.now());
    }

    String validateShortCode(String shortCode) {
        if (shortCode == null || shortCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }
        return shortCode;
    }

    String validateFullUrl(String fullUrl) {
        if (fullUrl == null || fullUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Full URL cannot be null or empty");
        }
        return fullUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UrlMapping that = (UrlMapping) obj;
        return Objects.equals(shortCode, that.shortCode) &&
               Objects.equals(fullUrl, that.fullUrl) &&
               Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortCode, fullUrl, createdAt);
    }

    @Override
    public String toString() {
        return "UrlMapping{" +
                "shortCode='" + shortCode + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}