package datalake.cloud;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.*;
import java.nio.channels.Channels;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class CloudDataLake {
    private String projectId;
    private String bucketName;
    private Bucket bucket;
    private Credentials credentials;
    private Storage storage;

    private Map<Integer, String> bookNames;
    private BookPersistenceCloud persistence;

    public CloudDataLake() {
        this.projectId = "search-engine-bd";
        this.bucketName = "ellagodelosdatos";
        initializeCloudStorage();
        persistence = new BookPersistenceCloud(this.bucketName, this.storage);
        bookNames = persistence.load();
    }

    public int addBook(String bookTitle) {
        int bookIndex = 0;
        if (!bookNames.isEmpty()) {
            bookIndex = Collections.max(bookNames.keySet()) + 1;
        }
        bookNames.put(bookIndex, bookTitle);
        persistence.save(bookNames);
        return bookIndex;
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
            this.credentials = GoogleCredentials.fromStream(
                Objects.requireNonNull(getClass().getResourceAsStream("/credentials.json")));
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

    public Blob getBlob(String name) {
        return bucket.get(name);
    }

}


