package utils;

import fsystem.DataLake;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static utils.Common.extractBookName;
import static utils.Common.preprocessText;


public class Reader {
    private Set<String> stopwordsEng;
    private DataLake datalake;

    public Reader(DataLake dlake) {
        this.datalake = dlake;
        stopwordsEng = Common.loadStopwords();
    }


    public String getPathSeparator() {
        String currentOs = System.getProperty("os.name").toLowerCase();
        if (currentOs.contains("win")) {
            return "\\";
        } else {
            return "/";
        }
    }

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

            words = preprocessText(bookContent.toString(),stopwordsEng);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookName = extractBookName(fileName);
        Book book = new Book(bookName,words);
        datalake.addBook(book.getIndex(), book.getName());

        return book;
    }


}
