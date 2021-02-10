package com.hornblower.crawler.core;

import java.util.*;

import com.hornblower.crawler.parsing.JsonUtilities;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SearchWebsite {

    private static final String ARTICLE_TYPE = "ARTICLE";
    private static final String KNOWLEDGE_BASE_TYPE = "FILE";

    /**
     * With the urlList and the kind of HTML element to search for this extracts the contents and creates a list of pages
     * based on that
     * @param urlList
     * @param by
     * @return
     */
    public static List<JSONObject> search(List<String> urlList, By by) {
        WebDriver driver = new ChromeDriver();
        List<JSONObject> pages = new ArrayList<>();
        String pageTitle = "";
        String content = "";
        List<String> filterList = JsonUtilities.getTextFilterList();

        for (String url : urlList) {
            driver.get(url);
            pageTitle = driver.getTitle();
            WebElement result = driver.findElement(by);

            content = result.getText();
            content = clearOddCharacters(content);
            content = refactorContent(content, filterList);

            pages.add(newArticle(content, pageTitle, url));
        }

        driver.close();
        driver.quit();

        return pages;
    }

    /**
     * Create a new article with the content extracted from a web page
     * @param pageContent web page's content
     * @param pageTitle web page's title
     * @param pageURL web page's url
     */
    public static JSONObject newArticle(String pageContent, String pageTitle, String pageURL) {
        JSONObject article = new JSONObject();

        article.put("article_type", ARTICLE_TYPE);
        article.put("knowledge_base_type", KNOWLEDGE_BASE_TYPE);
        article.put("original_content", "");
        article.put("text", pageContent);
        article.put("title", pageTitle);
        article.put("url", pageURL);

        return article;
    }

    /**
     * Receives the content as extracted from the website and organizes it with \n and spaces necessary for clarity
     */
    private static String refactorContent(String content, List<String> filterList) {
        Scanner fileScanner = new Scanner(content);
        String newContent = "";
        String newLine = "";
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {

                newLine += " " + lineScanner.next();
            }
            lineScanner.close();
            newLine = newLine.trim();
            if (!filterList.contains(newLine)) {
                //System.out.println(newLine);
                newContent += newLine + "\n   ";
            }
            newLine = "";
        }
        fileScanner.close();

        return newContent;
    }

    /**
     * Clear all UI characters that won't be recognized by curls - Good idea to update this if you know any extra characters
     */
    private static String clearOddCharacters(String content) {
        content = content.replace("’", "'");
        content = content.replace("“", "\"");
        content = content.replace("”", "\"");
        content = content.replace("–", "-");
        content = content.replace("©", "");
        content = content.replace("®", "");

        return content;
    }

    /**
     * NOT NECESSARY AT THE MOMENT!!
     * Get the article title from the URL;
     * Decision made because most articles don't have an inline title
     */
    private static String getTitle(String url) {
        String newTitle = "";
        String split[] = url.split("/");
        String title = split[split.length - 1];
        title = title.replace("-", " ");
        title = capitalizeSentence(title);

        if (split.length > 4) {
            String parentTitle = split[split.length - 2];
            parentTitle = parentTitle.replace("-", " ");

            newTitle = capitalizeSentence(parentTitle) + ": " + title;
        } else {
            newTitle = title;
        }

        return newTitle;
    }

    /**
     * Capitalize a sentence
     */
    private static String capitalizeSentence(String sentence) {
        String words[] = sentence.split(" ");
        String capitalizedSentence = "";

        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizedSentence += first.toUpperCase() + afterfirst + " ";
        }

        return clearOddCharacters(capitalizedSentence.trim());
    }


}
