package cloud;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudIndexer {

    private Storage storage;
    private Bucket bucket;

    public CloudIndexer() {
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

    public void indexOne() {
        //Leer del datalake un libro
        Page<Blob> blobs = bucket.list();
        for (Blob blob : blobs.getValues()) {
            String content = new String(blob.getContent());
            String name = blob.getName();
        }
    }
}
