package Prototype;

/**
 * Created by chris on 09/12/16.
 */
public class BlogStat {

    //absolute values
    private int processedPages;
    private long totalArticles;
    private long totalLinks;
    private long totalWordCount;

    //relative values
    private double AVG_WordCount;
    private double AVG_CommentCount;

    //highest relative values
    private double MAX_WordCount;
    private double MAX_CommentCount;

    public BlogStat(){

        processedPages = 0;
        totalArticles = 0;
        totalLinks = 0;
        totalWordCount = 0;

        AVG_WordCount = 0;
        AVG_CommentCount = 0;

        MAX_CommentCount = 0;
        MAX_WordCount = 0;
    }

    public void incProcessedPages(){
        processedPages = processedPages++;
    }

    public void incTotalArticles(){
        totalArticles = totalArticles++;
    }

    public void incTotalLinks(int count){
        totalLinks += count;
    }

    public void incTotalWordCount(int count){
        totalWordCount =+ count;
    }

    public long calculateAVGWordCount(){

    }



}
