package serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import service.UrlEncoder;
import service.UrlRepository;
import service.UrlShortenerService;


public class UrlShortenerServiceImplTest {
    private UrlRepository urlRepository = mock(UrlRepository.class);
    private UrlEncoder urlEncoder = mock(UrlEncoder.class);
    private UrlShortenerService service = new UrlShortenerServiceImpl(urlRepository, urlEncoder);

    @Test
    public void testShortener() {
        String fullUrl = "https://intellias.com";
        String id = "123abc";
        String shortUrl = "xyz123";

        when(urlRepository.save(fullUrl)).thenReturn(id);
        when(urlEncoder.encode(id)).thenReturn(shortUrl);

        assertEquals(shortUrl, service.shortener(fullUrl));
    }

    @Test
    public void testFullURL() {
        String shortUrl = "xyz123";
        String id = "123abc";
        String fullUrl = "https://intellias.com";

        when(urlEncoder.decode(shortUrl)).thenReturn(id);
        when(urlRepository.findById(id)).thenReturn(fullUrl);

        assertEquals(fullUrl, service.fullURL(shortUrl));
    }
}

