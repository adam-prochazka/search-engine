package utils;

import cloud.CloudDatalake;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static utils.Common.*;

public class CloudReader{

    private String bucketName;
    private Storage storage;
    private CloudDatalake cloudDatalake = new CloudDatalake();
    private Set<String> stopwords;

    public CloudReader(){
        this.bucketName = cloudDatalake.getBucketName();
        this.storage = cloudDatalake.getStorage();
        this.stopwords = loadStopwords();

    }

    public Book readBook(Blob blob) {
        String fileName, bookName;
        List<String> words = new ArrayList<>();

        if (blob == null) {
            System.out.println("File not found in the bucket.");
            return null;
        }

        fileName = blob.getName();

        try (InputStream inputStream = new ByteArrayInputStream(blob.getContent());
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {

            StringBuilder bookContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bookContent.append(line).append('\n');
            }

            words = preprocessText(bookContent.toString(), stopwords);
        } catch ( IOException e) {
            e.printStackTrace();
        }

        bookName = extractBookName(fileName);
        Book book = new Book(bookName, words);
        cloudDatalake.addBook(book.getIndex(), book.getName());

        return book;
    }
}

