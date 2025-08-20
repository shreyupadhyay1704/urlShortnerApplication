package serviceImpl;

import config.ConfigurationManager;
import domain.UrlMapping;
import service.UrlShortenerService;
import util.Base62Encoder;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class UrlShortenerServiceImpl implements UrlShortenerService {
    
    private final ConcurrentHashMap<String, UrlMapping> urlMappings;
    private final AtomicLong counter;
    private final Base62Encoder encoder;
    private final ConfigurationManager config;
    
    public UrlShortenerServiceImpl() {
        this.encoder = new Base62Encoder();
        this.config = ConfigurationManager.getInstance();
        this.urlMappings = new ConcurrentHashMap<>();
        this.counter = new AtomicLong(config.getInitialCounter());
    }
    
    // Constructor for testing with custom encoder and config
    UrlShortenerServiceImpl(Base62Encoder encoder, ConfigurationManager config) {
        this.encoder = encoder;
        this.config = config;
        this.urlMappings = new ConcurrentHashMap<>();
        this.counter = new AtomicLong(config.getInitialCounter());
    }
    
    @Override
    public String createShortUrl(String fullUrl) {
        validateUrl(fullUrl);
        
        Optional<String> existingShortCode = findExistingShortCode(fullUrl);
        if (existingShortCode.isPresent()) {
            return existingShortCode.get();
        }
        
        return generateNewShortCode(fullUrl);
    }
    
    @Override
    public Optional<String> getFullUrl(String shortCode) {
    	String sc = shortCode.replace("https://sho.rt/", "");
        validateShortCode(sc);
        
        UrlMapping mapping = urlMappings.get(sc);
        return Optional.ofNullable(mapping).map(UrlMapping::getFullUrl);
    }
    

    
    private void validateUrl(String url) {
        if (isNullOrEmpty(url)) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
    }
    
    private void validateShortCode(String shortCode) {
        if (isNullOrEmpty(shortCode)) {
            throw new IllegalArgumentException("Short code cannot be null or empty");
        }
    }
    
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    
    private Optional<String> findExistingShortCode(String fullUrl) {
        return urlMappings.values()
                .stream()
                .filter(mapping -> mapping.getFullUrl().equals(fullUrl))
                .map(mapping ->  "https://sho.rt/" +  mapping.getShortCode())
                .findFirst();
    }
    
    private String generateNewShortCode(String fullUrl) {
        long currentId = counter.getAndIncrement();
        String shortCode = encoder.encode(
            currentId, 
            config.getBase62Chars(), 
            config.getBaseRadix()
        );
        
        UrlMapping mapping = new UrlMapping(shortCode, fullUrl);
        urlMappings.put(shortCode, mapping);
        
        return  "https://sho.rt/" + shortCode;
    }
}
