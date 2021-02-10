/*
 *
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2019
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 *
 */
package com.hornblower.crawler.articlesplit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;

public class Split {

    public static void splitPages(List<JSONObject> pages) {
        for(JSONObject page : pages) {
            newFile(page);
        }
    }

    private static void newFile(final JSONObject page) {

        try  {
            final String title = page.get("title")
                .toString()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "_")
                .replace("/", "")
                .replace("'", "")
                .replace(":", "")
                .replace("result/", "");
            final FileWriter newFile = new FileWriter(String.format("result/%s.%s", title, "json"));
            newFile.write(page.toJSONString());
            newFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
