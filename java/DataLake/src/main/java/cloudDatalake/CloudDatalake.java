package cloudDatalake;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.util.Map;

public class CloudDatalake {
    private String bucketName;
    private String objectName;
    private Map<Integer, String> bookNames;
    private BookPersistenceCloud persistence;

    public CloudDatalake(String projectId, String bucketName, String objectName) {
        this.bucketName = bucketName;
        this.objectName = objectName;
        initializeGoogleCloudStorage(projectId);
        persistence = new BookPersistenceCloud(this.bucketName, this.objectName);
        bookNames = persistence.load();
    }

    public void addBook(int bookIndex, String bookTitle) {
        bookNames.put(bookIndex, bookTitle);
        persistence.save(bookNames);
    }

    public boolean isBookInDataLake(String i) {
        return persistence.containsBook(i);
    }

    public String getTitle(int bookIndex) {
        return bookNames.get(bookIndex);
    }

    public String getCloudDataLakePath() {
        return "gs://" + bucketName + "/" + objectName;
    }

    public void saveToGoogleCloudStorage(String fileName, String content) {
        SaveToGoogleCloudStorage saveToGoogleCloudStorage = new SaveToGoogleCloudStorage(bucketName);
        saveToGoogleCloudStorage.saveBook(fileName, content);
    }

    private void initializeGoogleCloudStorage(String projectId) {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            storage.create(Bucket.newBuilder(bucketName).build());
        }
    }
}
