package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by chris on 09/12/16.
 */
public class BlogStat {

    //absolute values
    private int processedPages;
    /*
    currently no option to check if page is an article or smth else
     */
   // private long totalArticles;
    private long totalLinks;
    private long totalWordCount;
    private long totalCommentCount;

    private HashSet<String> visitedURLs;

    //relative values
    private double AVG_WordCount;
    private double AVG_CommentCount;

    //highest relative values
    private int MAX_WordCount;
    private int MAX_CommentCount;

    public BlogStat(){

        processedPages = 0;
       // totalArticles = 0;
        totalLinks = 0;
        totalWordCount = 0;

        visitedURLs = null;

        /*
          Averages are per article
           */
        AVG_WordCount = 0;
        AVG_CommentCount = 0;

        MAX_CommentCount = 0;
        MAX_WordCount = 0;
    }

    public int incProcessedPages(){

        return processedPages = processedPages++;

    }

    /*


    public long incTotalArticles(){
        return totalArticles = totalArticles++;
    }
    */

    public long incTotalLinks(int count){
       return totalLinks += count;
    }

    public long incTotalWordCount(int count){
        return totalWordCount =+ count;
    }

    public long incTotalCommentCount(int count){
        return totalCommentCount =+ count;
    }

    public double calculateAVGWordCount(){

        return totalWordCount/processedPages;

    }

    public double calculateAVGCommentCount(){

        return totalCommentCount/processedPages;
    }

    public int checkMAXWordCount(int count){

        if(count > MAX_WordCount){
            MAX_WordCount = count;

            return MAX_WordCount;

        }
        else{
            return MAX_WordCount;
        }
    }

    public int checkMAXCommentCount(int count){

        if(count > MAX_CommentCount){
            MAX_CommentCount = count;
            return MAX_CommentCount;
        }
        else{
            return MAX_CommentCount;
        }
    }

    public HashSet<String> addToSet(String href){

        visitedURLs.add(href);
        return visitedURLs;
    }

    public boolean checkSet(String href){

        return visitedURLs.contains(href);
    }

    private int countWords(String text){
        String trimmed = text.trim();
        return trimmed.isEmpty() ? 0 : trimmed.split("//s+").length;
    }



    public boolean processPage(Page page){

        /*
        First I gonna process all the attributes I can get without HTML parsing via Jericho

        --excluded:
        Comments(total,max,avg)
        Attributes including totalArticle
         */
        incProcessedPages();
        visitedURLs.add(page.getWebURL().getURL().toLowerCase());

        HtmlParseData parseData = (HtmlParseData) page.getParseData();
        Set<WebURL> links = parseData.getOutgoingUrls();

        totalLinks = incTotalLinks(links.size());

        int wordCount = countWords(parseData.getText());

        totalWordCount = incTotalWordCount(wordCount);
        AVG_WordCount = calculateAVGWordCount();

        MAX_WordCount = checkMAXWordCount(wordCount);


        return true;



    }

    public int getProcessedPages() {
        return processedPages;
    }

    public String[] outputStats(){

        String[] outputstrings = new String[4];

        outputstrings[0] = Integer.toString(processedPages);
        outputstrings[1] = Long.toString(totalWordCount);
        outputstrings[2] = Double.toString(AVG_WordCount);
        outputstrings[3] = Long.toString(totalLinks);

        return outputstrings;

    }



    /*
    TODO add mehthod to process page. Pass necessary arguments (WordCount, LinkCount, CommentCount)
    TODO implement check if page is an article. Needed for the totalArticle attribute
    TODO implement BlogStatController, the BlogStat should only be a class for values. Operations goto the Controller
     */



}
