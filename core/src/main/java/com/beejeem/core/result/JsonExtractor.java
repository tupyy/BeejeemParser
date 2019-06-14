package com.beejeem.core.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to extract a json from a text
 */
public class JsonExtractor {

    public static String extract(String text) {
        String EMPTY_JSON = "{}";

        Pattern pattern = Pattern.compile(".*(\\{.*}).*");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return EMPTY_JSON;
    }
}
