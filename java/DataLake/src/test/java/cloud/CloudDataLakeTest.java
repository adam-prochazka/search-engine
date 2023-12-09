package cloud;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CloudDataLakeTest extends TestCase {
    public static void main(String[] args) throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("datalake/src/files/google-bucket-credentials.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("search-engine-404311").build().getService();

        Bucket bucket = storage.get("ulpgc-big-data-search-engine-bucket");

        String value = "Content of the book 0";
        String name = "Book0";

        write(bucket, name, value);

        checkForFile(bucket, name, value);
    }

    private static void write(Bucket bucket, String name, String value) {
        byte[] bytes = value.getBytes(UTF_8);
        Blob blob = bucket.create(name, bytes);
    }

    private static void checkForFile(Bucket bucket, String name, String value) {
        Page<Blob> blobs = bucket.list();
        for (Blob blob: blobs.getValues()) {
            String content = new String(blob.getContent());
            String blobName = blob.getName();
        }
    }

}