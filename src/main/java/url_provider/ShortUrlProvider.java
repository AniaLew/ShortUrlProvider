package url_provider;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ShortUrlProvider {
    private static ShortUrlProvider instance;
    private Map<String, String> urlRepository;

    private ShortUrlProvider(Map<String, String> urlRepository) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.urlRepository = urlRepository;
    }

    public static ShortUrlProvider getInstance(Map<String, String> urlRepository) {
        if(instance == null) {
            instance = new ShortUrlProvider(urlRepository);
        }
        return instance;
    }

    public String getUrl(String urlCommand) {
        List<Character> parameters = getParametersFromCommand(urlCommand);
        String longUrl = getUrlFromUrlCommand(urlCommand);
        String shortUrl = "";
        for (char parameter : parameters) {
            switch (parameter) {
                case 'i':
                    if(parameters.contains('o')) {
                        String alias = getAliasFromCommand(urlCommand);
                        shortUrl = shortenUrl(alias);
                    } else {
                        shortUrl = shortenUrl();
                    }
                    if(isShortUrlInRepo(shortUrl)) {
                        return  "Error: Short URL already in use";
                    }
                    urlRepository.put(longUrl, shortUrl);
                    return shortUrl;
                case 's':
                    shortUrl = getUrlFromUrlCommand(urlCommand);
                    longUrl = getLongUrlFromRepository(shortUrl);
                    return longUrl;
            }
        }
        return shortUrl;
    }

    private String shortenUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append("http://smrtcd.rs/");
        builder.append(generateUrl());
        return builder.toString();
    }

    private String shortenUrl(String alias) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://smrtcd.rs/");
        builder.append(alias);
        return builder.toString();
    }

    private List<Character> getParametersFromCommand(String urlCommand) {
        String parameterRegex = "\\s-[ios]\\s";
        Pattern parameterPattern = Pattern.compile(parameterRegex);
        Matcher parameterMatcher = parameterPattern.matcher(urlCommand);
        List<Character> parameters = new ArrayList<>();
        while (parameterMatcher.find()) {
            parameters.add(parameterMatcher.group().charAt(2));
        }
        return parameters;
    }

    private String getAliasFromCommand(String urlCommand) {
        return urlCommand.split(" ")[4];
    }

    private String getUrlFromUrlCommand(String command) {
        return command.split(" ")[2];
    }

    private int getRandomNumberWithinRange(int minIncluding, int maxExcluding) {
        Random random = new Random();
        return random.nextInt(maxExcluding - minIncluding) + minIncluding;
    }

    private String generateUrl() {
        StringBuilder builder = new StringBuilder();
        int urlLength = getRandomNumberWithinRange(5, 11);
        for (int i = 0; i < urlLength; i++) {
            int charOption = getRandomNumberWithinRange(0, 3);
            int charNumber = 0;
            switch (charOption) {
                case 0:
                    charNumber = getRandomNumberWithinRange(48, 58);
                    builder.append((char) charNumber);
                    break;
                case 1:
                    charNumber = getRandomNumberWithinRange(65, 91);
                    builder.append((char) charNumber);
                    break;
                case 2:
                    charNumber = getRandomNumberWithinRange(97, 123);
                    builder.append((char) charNumber);
                    break;
            }
        }
        return builder.toString();
    }

    private String getLongUrlFromRepository(String shortUrl) {
        return urlRepository
                .entrySet()
                .stream()
                .filter(entry -> shortUrl.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.joining());
    }

    private boolean isShortUrlInRepo(String shortUrl) {
       List<String> shortUrls = new ArrayList<>(urlRepository.values());
       Iterator<String> iterator = shortUrls.iterator();
       while(iterator.hasNext()) {
           String next = iterator.next();
           if(next.equals(shortUrl)) {
               return true;
           }
       }
       return false;
    }
}
