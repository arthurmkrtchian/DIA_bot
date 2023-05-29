package models;


import config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class Yourls {
    private static final String signature = Config.getYourlsToken();

    public static String getShortUrl(String longUrl) throws IOException {
        String url = Config.getYourlsUrl();
        String apiUrl = url + "?signature=" + signature + "&action=shorturl&format=simple&url=" + URLEncoder.encode(longUrl, "UTF-8");
        URLConnection connection = new URL(apiUrl).openConnection();
        InputStream response = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        String shortUrl = reader.readLine();
        reader.close();
        response.close();
        System.out.println("Short URL: " + shortUrl);
        return shortUrl;
    }
}
