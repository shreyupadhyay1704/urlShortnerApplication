package service;

public interface UrlShortenerService {
    String shortener(String fullUrl);
    String fullURL(String shortUrl);
}
