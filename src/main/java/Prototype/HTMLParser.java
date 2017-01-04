package Prototype;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Chris on 27.12.2016.
 */
public class HTMLParser {

    private static final Logger logger = LogManager.getLogger(HTMLParser.class);

    public HTMLParser(){

    }

    public Document parsePage(Page page) {

        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        String html = htmlParseData.getHtml();

        //  Document doc = Jsoup.parse(html, "http://www.101places.de/");
        return Jsoup.parse(html);
    }

    public int countComments(Document doc)
    {

        Element commentDiv = doc.select("div#comments").first();

        try{

            String[] splitComment = commentDiv.ownText().split(" ");

            return Integer.parseInt(splitComment[0]);


        }
        catch (NullPointerException e){
            return 0;
        }


    }

    public LocalDate getDate(Document doc){

        Element dateDiv = doc.select("div.single-thumbnail-date").first();

        try{
            Locale germanLocale = new Locale("ger", "ger");

            logger.info(dateDiv.ownText());

            String dateString = dateDiv.ownText();

            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd. MMMM yyyy", Locale.GERMANY));
            //LocalDate date = LocalDate.parse(dateString);

            System.out.println(date.toString());

            return date;
        }
        catch (NullPointerException e){

            return null;
        }


    }
}
