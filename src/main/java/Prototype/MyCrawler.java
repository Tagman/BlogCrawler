package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
//import org.slf4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.regex.Pattern;

/**
 * Created by chris on 09/12/16.
 */
public class MyCrawler extends WebCrawler {

    //TODO adjust FILTERS pattern
    //Pattern for sites to be ignored, might have to adjust it tho
    private static final Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
                    "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    BlogStat blogStat;

   // private static  final Logger logger = LoggerFactory.getLogger(MyCrawler.class);

    private static final Logger logger = LogManager.getLogger(MyCrawler.class);

    public MyCrawler() {

        blogStat = new BlogStat();
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url){
        String href = url.getURL().toLowerCase();

        return !FILTERS.matcher(href).matches() && href.startsWith("http://www.101places.de/") && !blogStat.checkSet(href);
    }

    @Override
    public void visit(Page page) {
        logger.info("Visited: " + page.getWebURL().getURL());

        if(page.getParseData() instanceof HtmlParseData){

            blogStat.processPage(page);
        }

        if(blogStat.getProcessedPages() % 10 == 0) {
            dumpStats();
        }

    }
    
    public void dumpStats(){

        /*
        String[] strings = blogStat.outputStats();

        for (String string : strings) {

            System.out.println(string);
            
        }
        */

        int id = getMyId();

        logger.info("Crawler " + id + " > Processed Pages: " + blogStat.getProcessedPages());
        logger.info ("Crawler " + id + " > total word count: " + blogStat.getTotalWordCount());
        logger.info("Crawler " + id + " > total links: " + blogStat.getTotalLinks());
        logger.info("Crawler " + id + " > AVG word count: " + blogStat.getAVG_WordCount());
        logger.info("Crawler " + id + " > MAX Word count: " + blogStat.getMAX_WordCount());
        /*
        logger.info("Crawler {} > Total Links found {}", id, blogStat.getTotalLinks());
        logger.info("Crawler {} > Total Word Count {}", id, blogStat.getTotalWordCount());
        logger.info("Crawler {} > AVG Word Count {}", id, blogStat.getAVG_WordCount());

        */
    }
}
