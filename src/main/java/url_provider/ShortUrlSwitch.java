package url_provider;

import java.util.*;
import java.util.stream.Collectors;

public class ShortUrlSwitch {
    String charRange = "ABCDEFGHIJKLMNOPRSTUWVXYZabcdefghijklmnoprstuwvxyz0123456789";
    private static ShortUrlSwitch instance;
    private Map<String, String> urlRepository;

    private ShortUrlSwitch(Map<String, String> urlRepository) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.urlRepository = urlRepository;
    }

    public static ShortUrlSwitch getInstance(Map<String, String> urlRepository) {
        if(instance == null) {
            instance = new ShortUrlSwitch(urlRepository);
        }
        return instance;
    }

    public String getUrl(String urlCommand) {
        Set<Character> parameters = getParametersFromCommand(urlCommand);
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

    private Set<Character> getParametersFromCommand(String urlCommand) {
        String[] commandElements = urlCommand.trim().split(" ");
        Set<Character>parameters = new HashSet<>();
        for(int i=1; i < commandElements.length; i+=2){
            parameters.add(commandElements[i].charAt(1));
        }
        return parameters;
    }

    private String getAliasFromCommand(String urlCommand) {
        return urlCommand.trim().split(" ")[4];
    }

    private String getUrlFromUrlCommand(String command) {
        return command.trim().split(" ")[2];
    }

    private int getRandomNumberWithinRange(int minIncluding, int maxExcluding) {
        Random random = new Random();
        return random.nextInt(maxExcluding - minIncluding) + minIncluding;
    }

    private String generateUrl() {
        StringBuilder builder = new StringBuilder();
        int urlLength = getRandomNumberWithinRange(5, 11);
        for(int i = 0; i< urlLength; i++) {
            int index = getRandomNumberWithinRange(0, charRange.length());
            builder.append(charRange.charAt(index));
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
