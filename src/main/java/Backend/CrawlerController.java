package Backend;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//import org.slf4j.Logger;


/**
 * Created by chris on 14/12/16.
 */
public class CrawlerController {

    //private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);



    private static final Logger logger = LogManager.getLogger(CrawlerController.class);


    public static void main(String[] args){

       // Logger parentLogger = LoggerFactory.getLogger("edu.uci.ics.crawler4j");



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

            /*
            List<Object> crawlersLocalData = crawlController.getCrawlersLocalData();

            long totalLinks = 0;
            long totalWordCount = 0;
            int processedPages = 0;

            for (Object localData : crawlersLocalData) {
                BlogStat stat = (BlogStat) localData;
                totalLinks += stat.getTotalLinks();
                totalWordCount += stat.getTotalWordCount();
                processedPages += stat.getProcessedPages();
            }

            logger.info("Aggregated Statistics:");
            logger.info("\tProcessed Pages: " + processedPages);

            */

            BlogStat.writeStats();





        }catch (Exception e){
            logger.error("Fehler bei Initialisierung des Crawler");
        }






    }


}
