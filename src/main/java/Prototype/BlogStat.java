package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.util.*;


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

    private Set<String> visitedURLs = new HashSet<>();

    //relative values
    private double AVG_WordCount;
    private double AVG_CommentCount;

    //highest relative values
    private int MAX_WordCount;
    private int MAX_CommentCount;



    private Map<Integer, Map<Integer, Integer>> dateMap;


    HTMLParser parser;

    public BlogStat(){

        parser = new HTMLParser();

        dateMap = new HashMap<>();

        processedPages = 0;
       // totalArticles = 0;
        totalLinks = 0;
        totalWordCount = 0;


        /*
          Averages are per article
           */
        AVG_WordCount = 0;
        AVG_CommentCount = 0;

        MAX_CommentCount = 0;
        MAX_WordCount = 0;
    }

    public int incProcessedPages(){

        return processedPages = ++processedPages;

    }

    public long incTotalLinks(int count){
       return totalLinks += count;
    }

    public long incTotalWordCount(int count){
        return totalWordCount += count;
    }

    public long incTotalCommentCount(int count){
        return totalCommentCount += count;
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

    public Set<String> addToSet(String href){

        visitedURLs.add(href);
        return visitedURLs;
    }

    public boolean checkSet(String href){

        return visitedURLs.contains(href);
    }

    private int countWords(String text){
        String trimmed = text.trim();
        return trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
    }



    public boolean processPage(Page page){

        /*
        First I gonna process all the attributes I can get without HTML parsing via JSoup

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

        Document doc = parser.parsePage(page);
        int commentCount = parser.countComments(doc);

        LocalDate date = parser.getDate(doc);
        if(date != null){
            processDate(date);
        }

        totalCommentCount = incTotalCommentCount(commentCount);
        AVG_CommentCount = calculateAVGCommentCount();
        MAX_CommentCount = checkMAXCommentCount(commentCount);


        return true;



    }

    public int getProcessedPages() {
        return processedPages;
    }

    public long getTotalLinks() {
        return totalLinks;
    }

    public long getTotalWordCount() {
        return totalWordCount;
    }

    public long getTotalCommentCount() {
        return totalCommentCount;
    }

    public Set<String> getVisitedURLs() {
        return visitedURLs;
    }

    public double getAVG_WordCount() {
        return AVG_WordCount;
    }

    public double getAVG_CommentCount() {
        return AVG_CommentCount;
    }

    public int getMAX_WordCount() {
        return MAX_WordCount;
    }

    public int getMAX_CommentCount() {
        return MAX_CommentCount;
    }

    public String[] outputStats(){

        String[] outputstrings = new String[4];

        outputstrings[0] = Integer.toString(processedPages);
        outputstrings[1] = Long.toString(totalWordCount);
        outputstrings[2] = Double.toString(AVG_WordCount);
        outputstrings[3] = Long.toString(totalLinks);

        return outputstrings;

    }

    public void processDate(LocalDate date){


        int year = date.getYear();
        int month = date.getMonthValue();


        /*
        int year = 2016;
        int month = 4;
        */

        if(!dateMap.containsKey(year)){

            dateMap.put(year, new HashMap(){{put(month, 1);}});
        }
        else{

            if(!dateMap.get(year).containsKey(month)){
                dateMap.get(year).put(month, 1);
            }
            else{
                int count = dateMap.get(year).get(month);

                dateMap.get(year).put(month, ++count);
            }
        }
    }

    public void dumpDateStats(){


        //Commented out because its too much Info in Console, when every info is printed out
        //Logging and prints are only test if the methods work
        //all value will get saved in file, for processing

        /*
        for(Map.Entry<Integer, Map<Integer, Integer>> entry : dateMap.entrySet()) {

            Integer key = entry.getKey();

            for (Map.Entry<Integer,Integer> deep : entry.getValue().entrySet()) {

                System.out.println("Jahr: " + key + " Monat: " + deep.getKey() + " Anzahl an Artikel in Monat: " + deep.getValue());
            }

        }
        */


        for(Map.Entry<Integer, Map<Integer, Integer>> entry : dateMap.entrySet()) {

            int count = 0;
            int key = entry.getKey();

            for (Map.Entry<Integer, Integer> deep : entry.getValue().entrySet()) {
                count += deep.getValue();
            }

            System.out.println("Anzahl an Artikel in: " + key + " = " + count);
        }

    }



    /*
    TODO add mehthod to process page. Pass necessary arguments (WordCount, LinkCount, CommentCount)
    TODO implement check if page is an article. Needed for the totalArticle attribute
    TODO implement BlogStatController, the BlogStat should only be a class for values. Operations goto the Controller
     */



}
