package persistence.repository;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudRepository {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private final String bucketName = "carlosbucket";
    private final String path = "InvertedIndexRepository";

    public CloudRepository() {
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        if (storage.get(bucketName) == null) {
            storage.create(BucketInfo.of(bucketName));
        }
    }

    public BlobId getBlobId(String word) {
        return BlobId.of(bucketName, path + "/" + word);
    }

}
