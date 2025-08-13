package serviceImpl;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import service.UrlShortenerService;

public class UrlShortenerServiceImpl implements UrlShortenerService {
	 private final Map<String, String> idToUrl = new HashMap<>();
	    private final Map<String, String> urlToId = new HashMap<>();
	    private long counter = 1; // simple auto-increment ID

	   
	    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	    @Override
	    public String shortener(String fullUrl) {
	        // If already exists, return existing short code
	        if (urlToId.containsKey(fullUrl)) {
	            return urlToId.get(fullUrl);
	        }

	       
	        String shortCode = encode(counter++);
	        idToUrl.put(shortCode, fullUrl);
	        urlToId.put(fullUrl, shortCode);

	        return shortCode;
	    }

	    @Override
	    public String fullURL(String shortUrl) {
	        return idToUrl.get(shortUrl);
	    }

	    
	    private String encode(long id) {
	        StringBuilder sb = new StringBuilder();
	        while (id > 0) {
	            sb.append(BASE62.charAt((int) (id % 62)));
	            id /= 62;
	        }
	        return sb.reverse().toString();
	    }
    
    
}

