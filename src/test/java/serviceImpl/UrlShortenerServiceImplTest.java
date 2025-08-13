package serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;


public class UrlShortenerServiceImplTest 
 {
    
    private UrlShortenerServiceImpl urlShortener;
    
    @BeforeEach
    void setUp() {
        urlShortener = new UrlShortenerServiceImpl(); 
    }
    
    @Test
    @DisplayName("Should create short URL for valid full URL")
    void testBasicShortening() {
        String fullUrl = "https://www.google.com";
        String shortCode = urlShortener.shortener(fullUrl);
        
        assertNotNull(shortCode, "Short code should not be null");
        assertFalse(shortCode.isEmpty(), "Short code should not be empty");
        assertEquals(fullUrl, urlShortener.fullURL(shortCode), "Should retrieve original URL");
    }
    
    @Test
    @DisplayName("Should return same short code for duplicate URLs")
    void testDuplicateUrlHandling() {
        String fullUrl = "https://www.example.com";
        String shortCode1 = urlShortener.shortener(fullUrl);
        String shortCode2 = urlShortener.shortener(fullUrl);
        
        assertEquals(shortCode1, shortCode2, "Same URL should return same short code");
        assertEquals(fullUrl, urlShortener.fullURL(shortCode1), "Should still retrieve correct URL");
    }
    
    @Test
    @DisplayName("Should generate different short codes for different URLs")
    void testDifferentUrls() {
        String url1 = "https://www.google.com";
        String url2 = "https://www.facebook.com";
        String url3 = "https://www.github.com";
        
        String shortCode1 = urlShortener.shortener(url1);
        String shortCode2 = urlShortener.shortener(url2);
        String shortCode3 = urlShortener.shortener(url3);
        
        assertNotEquals(shortCode1, shortCode2, "Different URLs should have different short codes");
        assertNotEquals(shortCode2, shortCode3, "Different URLs should have different short codes");
        assertNotEquals(shortCode1, shortCode3, "Different URLs should have different short codes");
        
        assertEquals(url1, urlShortener.fullURL(shortCode1));
        assertEquals(url2, urlShortener.fullURL(shortCode2));
        assertEquals(url3, urlShortener.fullURL(shortCode3));
    }
    
    @Test
    @DisplayName("Should handle sequential URL creation correctly")
    void testSequentialCreation() {
        String[] urls = {
            "https://www.site1.com",
            "https://www.site2.com", 
            "https://www.site3.com"
        };
        
        String[] shortCodes = new String[urls.length];
        
        
        for (int i = 0; i < urls.length; i++) {
            shortCodes[i] = urlShortener.shortener(urls[i]);
        }
        
        
        for (int i = 0; i < urls.length; i++) {
            assertEquals(urls[i], urlShortener.fullURL(shortCodes[i]), 
                "Should retrieve correct URL for index " + i);
        }
    }
    
    @Test
    @DisplayName("Should return null for non-existent short code")
    void testNonExistentShortCode() {
        String nonExistentCode = "nonExistent123";
        String result = urlShortener.fullURL(nonExistentCode);
        
        assertNull(result, "Should return null for non-existent short code");
    }
    

    
    @Test
    @DisplayName("Should generate Base62 encoded short codes")
    void testBase62Encoding() {
        String fullUrl = "https://www.test.com";
        String shortCode = urlShortener.shortener(fullUrl);
        
        
        String base62Chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (char c : shortCode.toCharArray()) {
            assertTrue(base62Chars.indexOf(c) != -1, 
                "Short code should contain only Base62 characters. Found: " + c);
        }
    }
    
    @Test
    @DisplayName("Should handle URLs with special characters")
    void testUrlsWithSpecialCharacters() {
        String[] specialUrls = {
            "https://www.example.com/path?param=value&other=123",
            "https://subdomain.example.com:8080/path#fragment",
            "https://www.example.com/path with spaces",
            "https://www.example.com/äöü-unicode"
        };
        
        for (String url : specialUrls) {
            String shortCode = urlShortener.shortener(url);
            assertEquals(url, urlShortener.fullURL(shortCode), 
                "Should handle URL with special characters: " + url);
        }
    }
    
    @Test
    @DisplayName("Should maintain consistency after multiple operations")
    void testConsistencyAfterMultipleOperations() {
        String url = "https://www.consistency-test.com";
        
        
        String shortCode1 = urlShortener.shortener(url);
        String retrieved1 = urlShortener.fullURL(shortCode1);
        
       
        urlShortener.shortener("https://www.other1.com");
        urlShortener.shortener("https://www.other2.com");
        
        
        String shortCode2 = urlShortener.shortener(url);
        String retrieved2 = urlShortener.fullURL(shortCode2);
        
        assertEquals(shortCode1, shortCode2, "Should return same short code");
        assertEquals(retrieved1, retrieved2, "Should return same full URL");
        assertEquals(url, retrieved2, "Should match original URL");
    }
    
    @Test
    @DisplayName("Should handle large number of URLs efficiently")
    void testScalability() {
        int numberOfUrls = 1000;
        String[] urls = new String[numberOfUrls];
        String[] shortCodes = new String[numberOfUrls];
        
        
        for (int i = 0; i < numberOfUrls; i++) {
            urls[i] = "https://www.test" + i + ".com";
            shortCodes[i] = urlShortener.shortener(urls[i]);
        }
        
      
        for (int i = 0; i < numberOfUrls; i++) {
            assertEquals(urls[i], urlShortener.fullURL(shortCodes[i]), 
                "Should retrieve correct URL for index " + i);
        }
        
        
        for (int i = 0; i < numberOfUrls; i++) {
            for (int j = i + 1; j < numberOfUrls; j++) {
                assertNotEquals(shortCodes[i], shortCodes[j], 
                    "Different URLs should have different short codes");
            }
        }
    }
    
    @Test
    @DisplayName("Should handle null input to fullURL method")
    void testFullUrlWithNull() {
        String result = urlShortener.fullURL(null);
        assertNull(result, "Should return null when retrieving with null short code");
    }
    
    @Test
    @DisplayName("Should verify Base62 encoding starts from 'b' for first URL")
    void testFirstEncodedValue() {
       
        String firstUrl = "https://www.first.com";
        String shortCode = urlShortener.shortener(firstUrl);
        
        assertEquals("b", shortCode, "First short code should be 'b'");
    }
}

