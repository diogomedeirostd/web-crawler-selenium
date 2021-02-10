package com.hornblower.crawler.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class XMLParser {

    /**
     * Extracts every url from the "loc" elements of the sitemap, building a urlList
     * @param sitemap sitemap to parse
     * @return url list
     */
    public static List<String> parse(String sitemap) {
        List<String> urlList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(sitemap).get();
            Elements urls = doc.getElementsByTag("loc");

            for(Element url : urls) {
                urlList.add(url.text());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return urlList;
    }
}
