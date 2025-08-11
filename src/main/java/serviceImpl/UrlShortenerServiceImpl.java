package serviceImpl;

import service.UrlEncoder;
import service.UrlRepository;
import service.UrlShortenerService;

public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlRepository urlRepository;
    private final UrlEncoder urlEncoder;

    public UrlShortenerServiceImpl(UrlRepository urlRepository, UrlEncoder urlEncoder) {
        this.urlRepository = urlRepository;
        this.urlEncoder = urlEncoder;
    }

    @Override
    public String shortener(String fullUrl) {
        String id = urlRepository.save(fullUrl); // persists returns unique id
        return urlEncoder.encode(id);
    }

    @Override
    public String fullURL(String shortUrl) {
        String id = urlEncoder.decode(shortUrl);
        return urlRepository.findById(id);
    }
}

