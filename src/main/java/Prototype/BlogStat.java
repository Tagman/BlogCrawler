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
    private long totalCommentCount;

    //relative values
    private double AVG_WordCount;
    private double AVG_CommentCount;

    //highest relative values
    private int MAX_WordCount;
    private int MAX_CommentCount;

    public BlogStat(){

        processedPages = 0;
        totalArticles = 0;
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

        return processedPages = processedPages++;

    }

    public long incTotalArticles(){
        return totalArticles = totalArticles++;
    }

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

        return totalWordCount/totalArticles;

    }

    public double calculateAVGCommentCount(){

        return totalCommentCount/totalArticles;
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

    /*
    TODO add mehthod to process page. Pass necessary arguments (WordCount, LinkCount, CommentCount)
    TODO implement check if page is an article. Needed for the totalArticle attribute
     */



}
