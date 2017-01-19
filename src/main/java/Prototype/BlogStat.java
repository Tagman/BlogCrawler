package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;


/**
 * Created by chris on 09/12/16.
 */
public class BlogStat {

    //absolute values
    private static int processedPages;
    /*
    currently no option to check if page is an article or smth else
     */
   // private long totalArticles;
    private static long totalLinks;
    private static long totalWordCount;
    private static long totalCommentCount;

    private static Set<String> visitedURLs = new HashSet<>();

    //relative values
    private static double AVG_WordCount;
    private static double AVG_CommentCount;

    //highest relative values
    private static int MAX_WordCount;
    private static int MAX_CommentCount;

    private static int processedArticles;





    private static Map<Integer, Map<Integer, Integer>> dateMap = new HashMap<>();


    //static HTMLParser parser;

    public BlogStat(){

       // parser = new HTMLParser();

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

    public static int incProcessedPages(){

        return processedPages = ++processedPages;

    }

    public static long incTotalLinks(int count){
       return totalLinks += count;
    }

    public static long incTotalWordCount(int count){
        return totalWordCount += count;
    }

    public static long incTotalCommentCount(int count){
        return totalCommentCount += count;
    }

    public static double calculateAVGWordCount(){

        return totalWordCount/processedPages;

    }

    public static double calculateAVGCommentCount(){

        return totalCommentCount/processedArticles;
    }

    public static int checkMAXWordCount(int count){

        if(count > MAX_WordCount){
            MAX_WordCount = count;

            return MAX_WordCount;

        }
        else{
            return MAX_WordCount;
        }
    }

    public static int checkMAXCommentCount(int count){

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

    public static boolean checkSet(String href){

        return visitedURLs.contains(href);
    }

    private static int countWords(String text){
        String trimmed = text.trim();
        return trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
    }



    public static boolean processPage(Page page){

        /*
        First I gonna process all the attributes I can get without HTML parsing via JSoup

        --excluded:
        Comments(total,max,avg)
        Attributes including totalArticle
         */
        BlogStat.incProcessedPages();
        visitedURLs.add(page.getWebURL().getURL().toLowerCase());

        HtmlParseData parseData = (HtmlParseData) page.getParseData();
        Set<WebURL> links = parseData.getOutgoingUrls();

        totalLinks = BlogStat.incTotalLinks(links.size());


        int wordCount = BlogStat.countWords(parseData.getText());

        totalWordCount = BlogStat.incTotalWordCount(wordCount);
        AVG_WordCount = BlogStat.calculateAVGWordCount();
        MAX_WordCount = BlogStat.checkMAXWordCount(wordCount);



        Document doc = HTMLParser.parsePage(page);

        LocalDate date = HTMLParser.getDate(doc);
        if(date != null){
            processDate(date);
        }

        int commentCount = HTMLParser.countComments(doc);

        if(commentCount != 0){
            totalCommentCount = BlogStat.incTotalCommentCount(commentCount);
            AVG_CommentCount = BlogStat.calculateAVGCommentCount();
            MAX_CommentCount = BlogStat.checkMAXCommentCount(commentCount);
        }






        return true;



    }

    public static int getProcessedPages() {
        return processedPages;
    }

    public static long getTotalLinks() {
        return totalLinks;
    }

    public static long getTotalWordCount() {
        return totalWordCount;
    }

    public static long getTotalCommentCount() {
        return totalCommentCount;
    }

    public Set<String> getVisitedURLs() {
        return visitedURLs;
    }

    public static double getAVG_WordCount() {
        return AVG_WordCount;
    }

    public static double getAVG_CommentCount() {
        return AVG_CommentCount;
    }

    public static int getMAX_WordCount() {
        return MAX_WordCount;
    }

    public static int getMAX_CommentCount() {
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

    public static void incProcessedArticles(){
        processedArticles = ++processedArticles;
    }

    public static void processDate(LocalDate date){


        int year = date.getYear();
        int month = date.getMonthValue();

        incProcessedArticles();


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

    public static void dumpDateStats(){


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

    public static boolean writeStats(){

        LocalDate now = LocalDate.now();

        String target = "Stats" + now.toString() + ".csv";

        try{

            PrintWriter pw = new PrintWriter(new File(target));
            StringBuilder sb = new StringBuilder();

            sb.append('\n');
            sb.append("Processed Pages");
            sb.append(';');
            sb.append("Processed Articles");
            sb.append(';');
            sb.append("Total Links");
            sb.append(';');
            sb.append("Total WordCount");
            sb.append(';');
            sb.append("AVG_WordCount");
            sb.append(';');
            sb.append("MAX_WordCount");
            sb.append(';');
            sb.append("Total CommentCount");
            sb.append(';');
            sb.append("AVG CommentCount");
            sb.append(';');
            sb.append("MAX_CommentCount");
            sb.append(';');
            sb.append('\n');
            sb.append(processedPages);
            sb.append(';');
            sb.append(processedArticles);
            sb.append(';');
            sb.append(totalLinks);
            sb.append(';');
            sb.append(totalWordCount);
            sb.append(';');
            sb.append(AVG_WordCount);
            sb.append(';');
            sb.append(MAX_WordCount);
            sb.append(';');
            sb.append(totalCommentCount);
            sb.append(';');
            sb.append(AVG_CommentCount);
            sb.append(';');
            sb.append(MAX_CommentCount);
            sb.append('\n');
            sb.append('\n');

            sb.append("Anzahl an Artikel in");
            sb.append(';');
            sb.append("Jahr");
            sb.append('\n');

            for(Map.Entry<Integer, Map<Integer, Integer>> entry : dateMap.entrySet()) {

                int count = 0;
                int key = entry.getKey();

                for (Map.Entry<Integer, Integer> deep : entry.getValue().entrySet()) {
                    count += deep.getValue();
                }

                sb.append(count);
                sb.append(';');
                sb.append(key);
                sb.append('\n');
            }

            sb.append('\n');
            sb.append('\n');

            sb.append("Jahr;Monat;Anzahl");
            sb.append('\n');


            for(Map.Entry<Integer, Map<Integer, Integer>> entry : dateMap.entrySet()) {

                Integer key = entry.getKey();



                for (Map.Entry<Integer,Integer> deep : entry.getValue().entrySet()) {

                    System.out.println("Jahr: " + key + " Monat: " + deep.getKey() + " Anzahl an Artikel in Monat: " + deep.getValue());

                    sb.append(key);
                    sb.append(';');
                    sb.append(deep.getKey());
                    sb.append(';');
                    sb.append(deep.getValue());
                    sb.append('\n');
                }

            }



            pw.write(sb.toString());
            pw.close();
            System.out.println("Done");

        }catch (FileNotFoundException e){
            System.out.println("Datei zum Speichern wurde nicht gefunden");
        }



        return true;
    }



    /*
    TODO add mehthod to process page. Pass necessary arguments (WordCount, LinkCount, CommentCount)
    TODO implement check if page is an article. Needed for the totalArticle attribute
    TODO implement BlogStatController, the BlogStat should only be a class for values. Operations goto the Controller
     */



}
