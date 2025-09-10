package  com.intellias.urlshortener.serviceimpl;

import  com.intellias.urlshortener.config.ConfigurationManager;
import  com.intellias.urlshortener.domain.UrlMapping;
import  com.intellias.urlshortener.encoding.Base62EncodingService;
import  com.intellias.urlshortener.service.UrlShortenerService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class UrlShortenerServiceImpl implements UrlShortenerService {

    private final ConcurrentHashMap<String, UrlMapping> urlMappings;
    private final AtomicLong counter;
    private final Base62EncodingService encodingService;
    private final ConfigurationManager config;

    public UrlShortenerServiceImpl(Base62EncodingService encodingService, ConfigurationManager config) {
        this.encodingService = encodingService;
        this.config = config;
        this.urlMappings = new ConcurrentHashMap<>();
        this.counter = new AtomicLong(config.getInitialCounter());
    }

    @Override
    public String createShortUrl(String fullUrl) {
        if (isNullOrEmpty(fullUrl)) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        Optional<String> existingShortCode = findExistingShortCode(fullUrl);
        if (existingShortCode.isPresent()) {
            return existingShortCode.get();
        }

        return generateNewShortCode(fullUrl);
    }

    @Override
    public Optional<String> getFullUrl(String shortCode) {
        String cleanShortCode = extractShortCodeFromUrl(shortCode);
        
        if (isNullOrEmpty(cleanShortCode)) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }

        UrlMapping mapping = urlMappings.get(cleanShortCode);
        return Optional.ofNullable(mapping).map(UrlMapping::getFullUrl);
    }

    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String extractShortCodeFromUrl(String shortCode) {
        String baseUrl = config.getBaseUrl();
        return shortCode.replace(baseUrl, "");
    }

    public Optional<String> findExistingShortCode(String fullUrl) {
        return urlMappings.values()
                .stream()
                .filter(mapping -> mapping.getFullUrl().equals(fullUrl))
                .map(mapping -> config.getBaseUrl() + mapping.getShortCode())
                .findFirst();
    }

    public String generateNewShortCode(String fullUrl) {
        long currentId = counter.getAndIncrement();
        String shortCode = encodingService.encode(
            currentId,
            config.getBase62Chars(),
            config.getBaseRadix()
        );

        UrlMapping mapping = new UrlMapping(shortCode, fullUrl);
        urlMappings.put(shortCode, mapping);

        return config.getBaseUrl() + shortCode;
    }
}