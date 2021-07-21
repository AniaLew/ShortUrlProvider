package url_provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class ShortUrlSwitchTest {
   static Map<String, String> urlRepo;
    static ShortUrlSwitch urlSwitch;

    @BeforeAll
    public static void setup(){
        urlRepo = new HashMap<>();
        urlSwitch = ShortUrlSwitch.getInstance(urlRepo);
    }

    @Test
    void shouldReturnShortUrl() {
        //given
        String command = "urlsh -i http://myl-ong-url.com";
        //when
        String actual = urlSwitch.getUrl(command);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.contains("http://smrtcd.rs/"));
        Assertions.assertTrue(actual.length() > "http://smrtcd.rs/".length());
    }

    @Test
    void shouldReturnShortUrlWithAlias() {
        //given
        String command = "urlsh -i http://mylong-url.com -o MyAlias";
        //when
        String actual = urlSwitch.getUrl(command);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.contains("http://smrtcd.rs/"));
        Assertions.assertTrue(actual.length() > "http://smrtcd.rs/".length());
        Assertions.assertEquals("http://smrtcd.rs/MyAlias", actual);
    }

    @Test
    void shouldReturnWarningIfShortUrlTaken() {
        //given
        String command = "urlsh -i http://mylong-url.com -o MyAlias";
        urlRepo.put("http://mylong-url.com", "http://smrtcd.rs/MyAlias");
        //when
        String actual = urlSwitch.getUrl(command);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Error: Short URL already in use", actual);
    }

    @Test
    void shouldReturnLongUrlFromRepository() {
        //given
        String command = "urlsh -s http://smrtcd.rs/MyAlias";
        urlRepo.put("http://mylong-url-long.com", "http://smrtcd.rs/MyAlias");
        //when
        String actual = urlSwitch.getUrl(command);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("http://mylong-url-long.com", actual);
    }
}