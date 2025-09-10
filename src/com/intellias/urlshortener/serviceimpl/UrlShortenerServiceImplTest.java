package com.intellias.urlshortener.serviceimpl;

import com.intellias.urlshortener.config.ConfigurationManager;
import com.intellias.urlshortener.encoding.Base62EncodingServiceImpl;
import com.intellias.urlshortener.serviceimpl.UrlShortenerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrlShortenerServiceImplTest {

    private UrlShortenerServiceImpl urlShortener;

    @BeforeEach
    void setUp() {
        Base62EncodingServiceImpl encodingService = new Base62EncodingServiceImpl();
        ConfigurationManager config = ConfigurationManager.getInstance();
        urlShortener = new UrlShortenerServiceImpl(encodingService, config);
    }

    @Test
    @DisplayName("Should create a short URL for a valid full URL")
    void testBasicShortening() {
        String fullUrl = "https://www.google.com";
        String shortenedUrl = urlShortener.createShortUrl(fullUrl);

        assertNotNull(shortenedUrl, "Shortened URL should not be null");
        assertFalse(shortenedUrl.isEmpty(), "Shortened URL should not be empty");

        assertTrue(urlShortener.getFullUrl(shortenedUrl).isPresent(), "Should be able to retrieve original URL");
        assertEquals(fullUrl, urlShortener.getFullUrl(shortenedUrl).get(), "Retrieved URL should match the original");
    }

    @Test
    @DisplayName("Should return same short code for duplicate URLs")
    void testDuplicateUrlHandling() {
        String fullUrl = "https://www.example.com";
        String shortCode1 = urlShortener.createShortUrl(fullUrl);
        String shortCode2 = urlShortener.createShortUrl(fullUrl);

        assertEquals(shortCode1, shortCode2, "Same URL should return same shortened URL");
        assertEquals(fullUrl, urlShortener.getFullUrl(shortCode1).get(), "Should still retrieve correct URL");
    }

    @Test
    @DisplayName("Should return an empty Optional for a non-existent short code")
    void testNonExistentShortCode() {
        String nonExistentCode = "https://sho.rt/nonExistent123";
        assertFalse(urlShortener.getFullUrl(nonExistentCode).isPresent(), "Should return an empty Optional for non-existent short code");
    }

  @Test
    @DisplayName("Should throw IllegalArgumentException for null URL input")
    void testCreateShortUrlWithNull() {
        assertThrows(IllegalArgumentException.class, () -> urlShortener.createShortUrl(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for empty URL input")
    void testCreateShortUrlWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> urlShortener.createShortUrl(""));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for empty short code input")
    void testGetFullUrlWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> urlShortener.getFullUrl(""));
    }

    @Test
    @DisplayName("Should validate null or empty values correctly")
    void testIsNullOrEmpty() {
        assertTrue(urlShortener.isNullOrEmpty(null), "Should return true for null");
        assertTrue(urlShortener.isNullOrEmpty(""), "Should return true for empty string");
        assertTrue(urlShortener.isNullOrEmpty("   "), "Should return true for whitespace string");
        assertFalse(urlShortener.isNullOrEmpty("valid"), "Should return false for valid string");
    }

    @Test
    @DisplayName("Should extract short code from full URL correctly")
    void testExtractShortCodeFromUrl() {
        String baseUrl = "https://sho.rt/";
        String shortCode = "abc123";
        String fullShortUrl = baseUrl + shortCode;
        
        assertEquals(shortCode, urlShortener.extractShortCodeFromUrl(fullShortUrl), 
                     "Should extract short code correctly");
    }
}