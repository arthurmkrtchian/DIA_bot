import config.Config;
import models.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Processor {
    Config config = new Config();

    public List<Post> execute() {
        var publicationList = new ArrayList<Post>();

        try {
            Document document = Jsoup.connect("https://dia.dp.gov.ua/").get();
            Elements elements = document.select(".post"); //all posts from current section on website
            MySQL dataBase = new MySQL();
            var databaseLinks = dataBase.read(); //all documents in the existing database

            for (Element doc : elements) {
                if (!databaseLinks.contains(getLink(doc))) {
                    publicationList.add(new Post(doc));
                    if (!Config.isTestLaunch()){
                        dataBase.write(getLink(doc));
                    } else {
                        System.out.println("Test mode. Link:" + getLink(doc));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return publicationList;
    }

    private static String getLink(Element element) {
        return element.getElementsByTag("a").attr("href");
    }

}
