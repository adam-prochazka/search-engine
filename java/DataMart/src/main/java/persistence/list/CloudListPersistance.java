package persistence.list;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CloudListPersistance {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "carlosbucket";

    public List<Integer> read(String word) {
        Blob blob = storage.get(BlobId.of(bucketName, word));
        if (blob == null) {
            return new ArrayList<>();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(blob.getContent()))) {
            return (List<Integer>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String word, List<Integer> list) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(list);

            BlobId blobId = BlobId.of(bucketName, word);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
