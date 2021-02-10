package com.hornblower.crawler.parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class JsonUtilities {

    //CONSTANTS THAT TALKDESK EXPECTS - NO NEED TO CHANGE THESE
    private static final String ARTICLE_TYPE = "ARTICLE";
    private static final String KNOWLEDGE_BASE_TYPE = "FILE";

    private static JSONArray articleList = new JSONArray();

    /**
     * Create the json file with the appropriate format (MISSING THE CURL HEADER) based on the article list that was
     * created while parsing
     * @param fileName json file name
     */
    public static void newFile(String fileName) {

        JSONObject indexFile = new JSONObject();
        indexFile.put("articles", articleList);
        indexFile.put("knowledge_base_id", UUID.randomUUID().toString());

        try (FileWriter file = new FileWriter(fileName+".json")) {
            file.write(indexFile.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        articleList.clear();
    }

    /**
     * Parse a json file and import its' elements into a JSONArray
     * @param path
     * @return
     */
    public static JSONArray readJSON(String path) {
        JSONParser jsonParser = new JSONParser();

        JSONArray elements = new JSONArray();

        try (FileReader reader = new FileReader(path)) {
            Object obj = jsonParser.parse(reader);

            elements = (JSONArray) obj;

            return elements;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return elements;
    }

    /**
     * Parse list of language codes from json file
     * @return list of language codes
     */
    public static List<String> getLangList() {
        List<String> langList = new ArrayList<>();
        JSONArray elements = JsonUtilities.readJSON("utility/iso639-1_language-codes.json");

        for(Object o : elements) {
            if(o instanceof JSONObject) {
                langList.add(((JSONObject)o).get("alpha2").toString());
            }
        }
        return langList;
    }

    /**
     * Parse list of filters to be used on filtering web site content
     * (CHANGE FILE NAME - CONFIGURE GENERIC crawler-filters.json)
     * @return filter list
     */
    public static List<String> getTextFilterList() {
        List<String> filterList = new ArrayList<>();
        JSONArray elements = JsonUtilities.readJSON("utility/filters.json");

        for(Object o : elements) {
            if(o instanceof JSONObject) {
                filterList.add(((JSONObject)o).get("filter").toString());
            }
        }
        return filterList;
    }



}
