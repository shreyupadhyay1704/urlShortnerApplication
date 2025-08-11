package service;

public interface UrlEncoder {
    String encode(String id);           // id to short URL string
    String decode(String shortUrl);     // short URL string to id
}
