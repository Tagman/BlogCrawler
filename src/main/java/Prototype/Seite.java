package Prototype;

import edu.uci.ics.crawler4j.url.WebURL;

import java.net.URL;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Chris on 13.12.2016.
 */
public class Seite {

    private LocalDate timeStamp;
    private String text;
    private int commentCount;
    private long wordCount;
    private URL url;
    private String html;
    private Set<WebURL> outgoingUrls;

    public Seite(LocalDate timeStamp, String text, int commentCount, long wordCount, URL url, String html, Set<WebURL> outgoingUrls) {
        this.timeStamp = timeStamp;
        this.text = text;
        this.commentCount = commentCount;
        this.wordCount = wordCount;
        this.url = url;
        this.html = html;
        this.outgoingUrls = outgoingUrls;
    }

    public Seite() {
        timeStamp = null;
        text = null;
        commentCount = -1;
        wordCount =  -1;
        url = null;
        html = null;
        outgoingUrls = null;
    }

    /*
    Constructor for Seite Object, parsed without Jericho. Jericho is gonna be implemted after
     */

    public Seite(String text, long wordCount, URL url, String html, Set<WebURL> outgoingUrls) {
        this.text = text;
        this.wordCount = wordCount;
        this.url = url;
        this.html = html;
        this.outgoingUrls = outgoingUrls;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public String getText() {
        return text;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public long getWordCount() {
        return wordCount;
    }

    public URL getUrl() {
        return url;
    }

    public String getHtml() {
        return html;
    }

    public Set<WebURL> getOutgoingUrls() {
        return outgoingUrls;
    }
}
