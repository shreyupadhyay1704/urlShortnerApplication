package com.intellias.urlshortener.encoding;

public interface Base62EncodingService {
    String encode(long id, String base62Chars, int radix);
    long decode(String encoded, String base62Chars, int radix);
}