package Prototype;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by chris on 14/12/16.
 */
public class CrawlerController {

    public static void main(String[] args){
        String crawlStorageFolder = "/home/chris/crawler/data";
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        try {
            CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);


            crawlController.addSeed("http://www.101places.de/");
            // crawlController.addSeed("http://www.101places.de/uber-mich");

            crawlController.start(MyCrawler.class, numberOfCrawlers);

        }catch (Exception e){
            System.out.println("Fehler beim CrawlController");
        }


    }
}
