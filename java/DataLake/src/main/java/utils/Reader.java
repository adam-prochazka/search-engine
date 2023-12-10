package utils;

import datalake.DataLake;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Reader implements BookReader {
    private Set<String> stopwordsEng = new HashSet<>();
    private DataLake datalake;

    public Reader(DataLake dlake) {
        this.datalake = dlake;
        loadStopwords();
    }

    private void loadStopwords() {
        try (FileInputStream stopwordStream = new FileInputStream("datalake/src/files/en-stopwords.txt");
             InputStreamReader stopwordStreamReader = new InputStreamReader(stopwordStream, StandardCharsets.UTF_8);
             BufferedReader stopwordBufferedReader = new BufferedReader(stopwordStreamReader)) {

            String line;
            while ((line = stopwordBufferedReader.readLine()) != null) {
                stopwordsEng.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> preprocessText(String text) {
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

    @Override
    public String getPathSeparator() {
        String currentOs = System.getProperty("os.name").toLowerCase();
        if (currentOs.contains("win")) {
            return "\\";
        } else {
            return "/";
        }
    }

    @Override
    public Book readBook(String path) {
        String fileName,bookName;
        List<String> words = new ArrayList<>();
        File file = new File(path);
        fileName = file.getName();

        try (FileInputStream fileStream = new FileInputStream(path);
             InputStreamReader fileStreamReader = new InputStreamReader(fileStream, StandardCharsets.UTF_8);
             BufferedReader fileBufferedReader = new BufferedReader(fileStreamReader)) {

            StringBuilder bookContent = new StringBuilder();
            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                bookContent.append(line).append('\n');
            }

            words = preprocessText(bookContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookName = this.extractBookName(fileName);
        Book book = new Book(bookName,words);
        datalake.addBook(book.getIndex(), book.getName());

        return book;
    }

    public static String extractBookName(String fileName) {
        String[] parts = fileName.split("\\)");
        String extractedName = parts[1].replace("_", " ");
        extractedName = extractedName.substring(0, extractedName.length() - 4);
        extractedName = extractedName.substring(0, 1).toUpperCase() + extractedName.substring(1);

        return extractedName;
    }

}
