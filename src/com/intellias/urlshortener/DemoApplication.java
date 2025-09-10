package com.intellias.urlshortener;

import com.intellias.urlshortener.service.UrlShortenerService;

import com.intellias.urlshortener.serviceimpl.UrlShortenerServiceImpl;

import com.intellias.urlshortener.config.ConfigurationManager;

import com.intellias.urlshortener.encoding.Base62EncodingServiceImpl;

public class DemoApplication {

    public static void main(String[] args) {
        // Initialize dependencies
        Base62EncodingServiceImpl encodingService = new Base62EncodingServiceImpl();
        ConfigurationManager config = ConfigurationManager.getInstance();
        UrlShortenerService urlShortener = new UrlShortenerServiceImpl(encodingService, config);

        // Demo usage
        System.out.println("=== URL Shortener Demo ===");
        
        String shortCode1 = urlShortener.createShortUrl("https://www.google.com");
        String shortCode2 = urlShortener.createShortUrl("https://www.github.com");

        System.out.println("Short code for Google: " + shortCode1);
        System.out.println("Short code for GitHub: " + shortCode2);

        System.out.println("Retrieve full URLs - " + urlShortener.getFullUrl(shortCode1));

        String duplicateCode = urlShortener.createShortUrl("https://www.google.com");
        System.out.println("Duplicate short code: " + duplicateCode);
        System.out.println("Same as original: " + duplicateCode.equals(shortCode1));
        
        System.out.println("Demo completed successfully!");
    }
}