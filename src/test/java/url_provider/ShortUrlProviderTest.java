package url_provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class ShortUrlProviderTest {
    static ShortUrlProvider shortUrlProvider;
    static Map<String, String> urlRepository;

    @BeforeAll
    public static void setup() {
        urlRepository = new HashMap<>();
        shortUrlProvider = ShortUrlProvider.getInstance(urlRepository);
    }

    @Test
    void shouldReturnShortUrl() {
        //given
        String urlCommand = "urlsh -i http://mylong-url.com";
        //when
        String actualShortUrl = shortUrlProvider.getUrl(urlCommand);
        //then
        Assertions.assertNotNull(actualShortUrl);
        String expectedShortUrl = getShortUrlFromRepo(urlCommand);
        Assertions.assertEquals(expectedShortUrl, actualShortUrl);
    }

    @Test
    void shouldReturnShortUrlWithAlias() {
        //given
        String urlCommand = "urlsh -i http://mylong-url.com -o MyString";
        //when
        String actualShortUrl = shortUrlProvider.getUrl(urlCommand);
        //then
        Assertions.assertNotNull(actualShortUrl);
        Assertions.assertEquals("http://smrtcd.rs/MyString", actualShortUrl);
    }

    @Test
    void shouldReturnLongUrlFromRepository() {
        //given
        urlRepository.put("http://mylong-url-for-test.com", "http://smrtcd.rs/ShortUrl");
        String urlCommand = "urlsh -s http://smrtcd.rs/ShortUrl";
        //when
        String actualLongUrl = shortUrlProvider.getUrl(urlCommand);
        //then
        Assertions.assertNotNull(actualLongUrl);
        Assertions.assertEquals("http://mylong-url-for-test.com", actualLongUrl);
    }

    @Test
    void shouldReturnErrorIfShortUriAlreadyTaken() {
        //given
        urlRepository.put("http://my-other-long-url.com", "http://smrtcd.rs/MyNewShort");
        String urlCommand = "urlsh -i http://my-other-long-url.com -o MyNewShort";
        //when
        String actual = shortUrlProvider.getUrl(urlCommand);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Error: Short URL already in use", actual);
    }

    private String getShortUrlFromRepo(String urlCommand) {
        String longUrl = urlCommand.split(" ")[2];
        return urlRepository
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(longUrl))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

    }
}