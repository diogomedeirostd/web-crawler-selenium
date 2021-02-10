package com.hornblower.crawler.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegExParser {

    /**
     * By receiving a language code list, goes to the list of all languages, removes those from the list and then uses
     * the remaining list as a blacklist for the urlList. URLs with no language code always get through (default english?)
     * @param urlList urls from sitemap
     * @return filtered url list
     */
    public static List<String> urlLanguageParser(List<String> extraAllowedLangs, List<String> urlList) {
        List<String> newUrlList = new ArrayList<>();
        List<String> langList = JsonUtilities.getLangList();

        for (String lang : extraAllowedLangs) { //remove the chosen languages so they are crawled
            langList.remove(lang);
        }

        for (String lang : langList) {
            Pattern pattern = Pattern.compile(".*(/" + lang + "/).*");

            for (String url : urlList) {
                Matcher m = pattern.matcher(url);
                if (m.matches()) {
                    newUrlList.add(url);
                }
            }
        }
        urlList.removeAll(newUrlList);

        return urlList;
    }

    /**
     * Filters the url list using specific regex patterns
     * @param patterns list of patterns to filter by
     * @param urlList urls from sitemap
     * @return filtered url list
     */
    public static List<String> urlPatternParser(List<String> patterns, List<String> urlList) {
        List<String> newUrlList = new ArrayList<>();

        for (String p : patterns) {
            Pattern pattern = Pattern.compile(p);

            for (String url : urlList) {
                Matcher m = pattern.matcher(url);
                if (m.matches()) {
                    newUrlList.add(url);
                }
            }
        }
        return newUrlList;
    }

    /**
     * To be used as a way to apply filters that remove "trash" from the whole content of the crawled websites
     * @param content content extracted from the website
     * @param patternList patterns to filter by
     */
    public static void filterExtractedContent(String content, List<Pattern> patternList) {
        //Process article content and delete everything that matches any pattern
    }

}
