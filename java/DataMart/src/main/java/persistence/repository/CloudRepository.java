package persistence.repository;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudRepository {
    private Storage storage;
    private final String projectId;
    private Credentials credentials;
    private final String bucketName;
    private Bucket bucket;

    public CloudRepository() {
        this.projectId = "search-engine-bd";
        this.bucketName = "cloud_datamart";
        initializeCloudStorage();
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

    public Storage getStorage(){
        return this.storage;
    }

    public String getBucketName(){
        return this.bucketName;
    }



}
