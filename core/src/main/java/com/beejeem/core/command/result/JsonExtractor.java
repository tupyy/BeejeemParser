package com.beejeem.core.command.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to extract a json from a text
 */
public final class JsonExtractor {

    private JsonExtractor() {
        throw new IllegalStateException("Utility class");
    }

    public static String extract(String text) {
        Pattern pattern = Pattern.compile(".*(\\{.*}).*");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "{}";
    }
}
