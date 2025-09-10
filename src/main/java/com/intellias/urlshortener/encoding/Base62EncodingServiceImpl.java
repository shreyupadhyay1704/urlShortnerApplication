package com.intellias.urlshortener.encoding;

public final class Base62EncodingServiceImpl implements Base62EncodingService {

    @Override
    public String encode(long id, String base62Chars, int radix) {
        validateEncodeParameters(id, base62Chars, radix);

        if (id == 0) return String.valueOf(base62Chars.charAt(0));

        StringBuilder result = new StringBuilder();

        while (id > 0) {
            result.append(base62Chars.charAt((int) (id % radix)));
            id /= radix;
        }

        return result.reverse().toString();
    }

    @Override
    public long decode(String encoded, String base62Chars, int radix) {
        validateDecodeParameters(encoded, base62Chars, radix);

        long result = 0;
        long power = 1;

        for (int i = encoded.length() - 1; i >= 0; i--) {
            char character = encoded.charAt(i);
            int index = base62Chars.indexOf(character);

            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in encoded string: " + character);
            }

            result += index * power;
            power *= radix;
        }

        return result;
    }

    private void validateEncodeParameters(long id, String base62Chars, int radix) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        if (base62Chars == null || base62Chars.isEmpty()) {
            throw new IllegalArgumentException("Base62 characters cannot be null or empty");
        }
        if (radix <= 1 || radix > base62Chars.length()) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
    }

    private void validateDecodeParameters(String encoded, String base62Chars, int radix) {
        if (encoded == null || encoded.isEmpty()) {
            throw new IllegalArgumentException("Encoded string cannot be null or empty");
        }
        if (base62Chars == null || base62Chars.isEmpty()) {
            throw new IllegalArgumentException("Base62 characters cannot be null or empty");
        }
        if (radix <= 1 || radix > base62Chars.length()) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
    }
}
