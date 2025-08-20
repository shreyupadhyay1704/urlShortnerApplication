package service;

import java.util.Optional;

public interface UrlShortenerService {
    /**
     * Creates a short URL for the given full URL.
     * Returns existing short code if URL was already shortened.
     * 
     * @param fullUrl the URL to shorten
     * @return the short code
     * @throws IllegalArgumentException if fullUrl is null or empty
     */
    String createShortUrl(String fullUrl);
    
    /**
     * Retrieves the full URL for the given short code.
     * 
     * @param shortCode the short code to expand
     * @return Optional containing the full URL, or empty if not found
     * @throws IllegalArgumentException if shortCode is null or empty
     */
    Optional<String> getFullUrl(String shortCode);
    
   
}

