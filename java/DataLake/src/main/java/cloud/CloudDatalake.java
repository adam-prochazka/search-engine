package cloud;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CloudDatalake {

    private Storage storage;
    private Bucket bucket;

    public CloudDatalake() {
        try {
            Credentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream("datalake/src/files/google-bucket-credentials.json"));

            this.storage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId("search-engine-404311").build().getService();
             this.bucket = storage.get("ulpgc-big-data-search-engine-bucket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isBookInDataLake(String i) {
        String fileName = "(" + i + ")" + ".txt";
        Blob blob = storage.get(BlobId.of("ulpgc-big-data-search-engine-bucket", fileName));
        return blob != null;
    }

    public void saveToGoogleCloudStorage(String fileName, String bookContent) {
        byte[] bookContentBytes = bookContent.getBytes(StandardCharsets.UTF_8);
        Blob blob = bucket.create(fileName, bookContentBytes);
        System.out.println("Libro subido al Cloud Storage: " + blob.getName());
    }
}
