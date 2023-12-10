package cloud;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;

public class SaveToGoogleCloudStorage {
    private String projectId;
    private String bucketName;

    public SaveToGoogleCloudStorage(String projectId, String bucketName) {
        this.projectId = projectId;
        this.bucketName = bucketName;
    }

    public void saveBook(String objectName, String content) {
        try {
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            Blob blob = storage.get(bucketName, objectName);
            if (blob == null) {
                blob = storage.create(Blob.newBuilder(bucketName, objectName).build());
            }

            OutputStream outputStream = Channels.newOutputStream(blob.writer());
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
