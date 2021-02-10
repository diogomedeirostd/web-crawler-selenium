package com.hornblower.crawler.core;

import com.hornblower.crawler.articlesplit.Split;
import com.hornblower.crawler.parsing.RegExParser;
import com.hornblower.crawler.parsing.XMLParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;

import java.util.List;

public class Crawler {

    public Crawler() {
    }

    /**
     * Calls every necessary service in order to parse the sitemap, get the filtered by languages url list, search those urls and
     * finally get the content on json files
     *
     * @param sitemap sitemap with urls to crawl
     * @param extraAllowedLangs list of languages to filter by
     */
    public static void startSitemapCrawlByLangs(String sitemap, List<String> extraAllowedLangs) {
        //1. Get url list from sitemap
        List<String> urlList = XMLParser.parse(sitemap);
        //2. Filter unwanted languages' URLs out of the list (English is always included)
        List<String> newUrlList = RegExParser.urlLanguageParser(extraAllowedLangs, urlList);
        //3. Get content from the website
        List<JSONObject> pages = SearchWebsite.search(newUrlList, By.tagName("body"));
        //4. Split the content of the pages into single files
        Split.splitPages(pages);
    }

    /**
     * This Method is not implemented. The idea was to have a list of regex patterns in order to filter the page with them and remove some unnecessary text with it.
     * Calls every necessary service in order to parse the sitemap, get the filtered by patterns url list, search those urls and
     * finally get the content on json files
     *
     * @param sitemap sitemap with urls to crawl
     * @param patternList list of patterns to filter by
     * @param fileName json file name
     */
    public static void startSitemapCrawlByPatterns(String sitemap, List<String> patternList, String fileName) {
        List<String> urlList = XMLParser.parse(sitemap);
        List<String> newUrlList = RegExParser.urlPatternParser(patternList, urlList);
        List<JSONObject> pages = SearchWebsite.search(newUrlList, By.tagName("body"));
        Split.splitPages(pages);
    }

}
