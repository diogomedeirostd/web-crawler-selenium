package com.hornblower.crawler;

import com.hornblower.crawler.core.Crawler;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static Crawler crawler = new Crawler();

    public static void main (String [] args) {

        /**
         * IF THERE ARE ANY LANGUAGES BESIDES ENGLISH THAT YOU WANT THE PAGES TO BE INCLUDED, UNCOMMENT THIS CODE AND ADD THE LANGUAGE CODE
         * FOR REFERENCE OF THE CODE YOU CAN USE THE FILE utility/iso639-1_language_codes.json WHICH HAS A FULL LIST
         */
        List<String> extraAllowedLangs = new ArrayList<>();
        //langs.add("");


        /**
         * REPLACE THE ALCATRAZ SITEMAP WITH THE ONE YOU WANT TO CRAWL
         */
        Crawler.startSitemapCrawlByLangs("https://www.alcatrazcruises.com/sitemap.xml", extraAllowedLangs);

    }
}
