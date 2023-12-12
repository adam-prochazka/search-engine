package crawler.ctrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentManager {

    public static String getBookTitle(String bookContent){
        String[] lines = bookContent.split("\n");
        String title = null;
        for (String line : lines) {
            if (line.startsWith("Title:")) {
                title = line.replace("Title:", "").trim();
                break;
            }
        }

        return title;
    }

    public static String cleanFilename(String title) {

        String[][] replacements = {
                {":", " "}, {" ", "_"}, {",", ""}, {".", ""},
                {"(", " "}, {")", ""}, {"-", ""}, {"/", ""},
                {";", ""}, {"\"", ""}, {"—", ""}, {"?", ""}
        };

        for (String[] replacement : replacements) {
            title = title.replace(replacement[0], replacement[1]);
        }

        return title;

    }

    public static String getBookContent(String content){

        Pattern regex1 = Pattern.compile("\\*\\*\\* START OF THE PROJECT GUTENBERG EBOOK(.*)", Pattern.DOTALL);
        Pattern regex2 = Pattern.compile("\\*\\*\\* START OF THE PROJECT GUTENBERG EBOOK(.*)", Pattern.DOTALL);

        String bookContent = "";

        Matcher matcher1 = regex1.matcher(content);
        Matcher matcher2 = regex2.matcher(content);

        if (matcher1.find()) {
            bookContent = matcher1.group(1).trim();
        } else if (matcher2.find()) {
            bookContent = matcher2.group(1).trim();
        }

        return bookContent.split("\\*\\*\\*")[1].trim();
    }
}
