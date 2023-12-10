package cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Map;

public class CloudDatalake {
    private String projectId;
    private String bucketName;
    private Bucket bucket;
    private Credentials credentials;
    private Storage storage;

    private Map<Integer, String> bookNames;
    private BookPersistenceCloud persistence;

    public CloudDatalake() {
        this.projectId = "search-engine-bd";
        this.bucketName = "ellagodelosdatos";
        initializeCloudStorage();
        persistence = new BookPersistenceCloud(this.bucketName, this.storage);
        bookNames = persistence.load();
    }

    public void addBook(int bookIndex, String bookTitle) {
        bookNames.put(bookIndex, bookTitle);
        persistence.save(bookNames);
    }

    public boolean isBookInDataLake(String i) {
        Iterable<Blob> blobs = storage.list(bucketName).iterateAll();

        for (Blob blob : blobs) {
            String fileName = blob.getName();
            if (fileName.contains("(" + i + ")")) {
                return true;
            }
        }

        return false;
    }

    public String getTitle(int bookIndex) {
        return bookNames.get(bookIndex);
    }

    public String getCloudDataLakePath() {
        return "gs://" + bucketName ;
    }

    public void saveToCloud(String fileName, String content) {
            try {
                Blob blob = storage.get(bucketName, fileName);
                if (blob == null) {
                    blob = storage.create(Blob.newBuilder(bucketName, fileName).build());
                }

                OutputStream outputStream = Channels.newOutputStream(blob.writer());
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void initializeCloudStorage() {
        try {
            this.credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\Carlos\\Documents\\PycharmProjects\\BD\\search-engine\\java\\DataLake\\src\\main\\resources\\credentials.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(this.projectId).build().getService();

        this.bucket = storage.get(this.bucketName);
    }


    public String getBucketName() {
        return bucketName;
    }

    public Storage getStorage() {
        return storage;
    }



}
