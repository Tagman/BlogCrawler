package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

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
        System.out.println("Visited: " + page.getWebURL().getURL());

        if(page.getParseData() instanceof HtmlParseData){

            blogStat.processPage(page);
        }

    }
}
