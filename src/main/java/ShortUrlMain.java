import service.UrlShortenerService;
import serviceImpl.UrlShortenerServiceImpl;

public class ShortUrlMain {

	public static void main(String[] args) {
        UrlShortenerService service = new UrlShortenerServiceImpl();

        String fullUrl = "https://example.com/clean-architecture";
        String shortCode = service.shortener(fullUrl);

        System.out.println("Original: " + fullUrl);
        System.out.println("Shortened: https://sho.rt/" + shortCode);

        String expanded = service.fullURL(shortCode);
        System.out.println("Expanded: " + expanded);
    }
	
}
