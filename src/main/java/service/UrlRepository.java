package service;

public interface UrlRepository {
    String save(String fullUrl);        // returns unique id for URL
    String findById(String id);         // returns full URL
}
