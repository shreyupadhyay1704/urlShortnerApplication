package serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrlShortenerServiceImplTest {

    private UrlShortenerServiceImpl urlShortener;

    @BeforeEach
    void setUp() {
       
        urlShortener = new UrlShortenerServiceImpl();
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
   
}

