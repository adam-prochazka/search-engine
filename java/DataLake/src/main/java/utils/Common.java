package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Common {

    public static Set<String> loadStopwords() {
        Set<String> stopwords = new HashSet<>();
        try (FileInputStream stopwordStream = new FileInputStream("datalake/src/files/en-stopwords.txt");
             InputStreamReader stopwordStreamReader = new InputStreamReader(stopwordStream, StandardCharsets.UTF_8);
             BufferedReader stopwordBufferedReader = new BufferedReader(stopwordStreamReader)) {

            String line;
            while ((line = stopwordBufferedReader.readLine()) != null) {
                stopwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopwords;
    }

    public static List<String> preprocessText(String text,Set<String>stopwordsEng) {
        List<String> words = new ArrayList<>();

        String[] tokens = text.replace('—', ' ').replace('.', ' ')
                .replace('_', ' ').replace('=', ' ').replace('-', ' ')
                .split("\\s+");

        for (String token : tokens) {
            String word = token.toLowerCase().replaceAll("[^a-zA-Z]", "");
            if (word.matches(".*[a-zA-Z].*") && !stopwordsEng.contains(word) && !word.isEmpty()) {
                words.add(word);
            }
        }

        return words;
    }

    public static String extractBookName(String fileName) {
        String[] parts = fileName.split("\\)");
        String extractedName = parts[1].replace("_", " ");
        extractedName = extractedName.substring(0, extractedName.length() - 4);
        extractedName = extractedName.substring(0, 1).toUpperCase() + extractedName.substring(1);

        return extractedName;
    }
}
