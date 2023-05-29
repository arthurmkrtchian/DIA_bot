package models;

import config.Config;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class Post {
    private final String caption;
    private final String details;
    private final String link;


    public Post(Element element) {
        String captionTemp = element.getElementsByTag("a").text();
        if (captionTemp.endsWith(" 0")) {
            this.caption = "\uD83C\uDDFA\uD83C\uDDE6 " + captionTemp.substring(0, captionTemp.length() - 1);
        } else {
            this.caption = "\uD83C\uDDFA\uD83C\uDDE6 " + captionTemp;
        }
        System.out.println(this.caption);
        this.details = element.select(".post-excerpt").text() + "...";
        this.link = element.getElementsByTag("a").attr("href");
    }

    @Override
    public String toString() {
        try {
            if (Config.isTestLaunch()) {
                return "Test Launch. Document caption: " + this.caption;
            }
            return this.caption + "\n\n" +
                    this.details + "\n\n" +
                    Yourls.getShortUrl(this.link);
        } catch (IOException e) {
            System.out.println("Can`t get the short link. Maybe it`s already exists in models.Yourls database. Or check the yourls service url and signature.");
        }
        return null;
    }


    public String getCaption() {
        return caption;
    }

    public String getDate() {
        return details;
    }

    public String getLink() {
        return link;
    }
}

