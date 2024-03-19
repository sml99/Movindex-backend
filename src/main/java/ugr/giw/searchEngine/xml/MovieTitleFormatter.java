package ugr.giw.searchEngine.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieTitleFormatter {
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"'(.*)'\" \\((\\d{4})\\) \\{\\((.*)\\)\\}");

    public static String formatTitle(String rawTitle) {
        Matcher matcher = TITLE_PATTERN.matcher(rawTitle);
        if (matcher.find()) {
            String title = matcher.group(1);
            String year = matcher.group(2);
            String date = matcher.group(3);

            // Format the title as needed
            String formattedTitle = title + " (" + year + ") - " + date;
            System.out.println("Formatted title: " + formattedTitle);

            return formattedTitle;
        } else {
            throw new IllegalArgumentException("Invalid title format: " + rawTitle);
        }
    }
}