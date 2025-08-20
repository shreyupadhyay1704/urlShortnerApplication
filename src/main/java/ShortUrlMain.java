import service.UrlShortenerService;
import serviceImpl.UrlShortenerServiceImpl;

public class ShortUrlMain {

	  public static void main(String[] args) {
	        UrlShortenerService urlShortener = new UrlShortenerServiceImpl();
	        
	        
	        String shortCode1 = urlShortener.createShortUrl("https://www.google.com");
	        String shortCode2 = urlShortener.createShortUrl("https://www.github.com");
	        
	        System.out.println("Short code for Google: " + shortCode1);
	        System.out.println("Short code for GitHub: " + shortCode2);
	        
	        
	        urlShortener.getFullUrl(shortCode1);
	                 
	        System.out.println("Retrieve full URLs - " +  urlShortener.getFullUrl(shortCode1));
	        
	       
	        
	        String duplicateCode = urlShortener.createShortUrl("https://www.google.com");
	        System.out.println("Duplicate short code: " + duplicateCode);
	        System.out.println("Same as original: " + duplicateCode.equals(shortCode1));
	    }
	
}
